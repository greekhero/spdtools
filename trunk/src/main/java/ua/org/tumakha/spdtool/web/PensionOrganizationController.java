package ua.org.tumakha.spdtool.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.org.tumakha.spdtool.AppConfig;
import ua.org.tumakha.spdtool.entity.PensionOrganization;
import ua.org.tumakha.spdtool.services.PensionOrganizationService;
import ua.org.tumakha.spdtool.web.util.WebUtil;

/**
 * @author Yuriy Tumakha
 */
@RequestMapping("/pension_organization")
@Controller
public class PensionOrganizationController implements AppConfig {

	@Autowired
	private PensionOrganizationService pensionOrganizationService;

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid PensionOrganization pensionOrganization, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("organization", pensionOrganization);
			return "pension_organization/create";
		}
		try {
			pensionOrganizationService.createPensionOrganization(pensionOrganization);
		} catch (Exception e) {
			uiModel.addAttribute("organization", pensionOrganization);
			addError(uiModel, e);
			return "pension_organization/create";
		}
		uiModel.asMap().clear();
		return "redirect:/pension_organization/"
				+ WebUtil.encodeUrlPathSegment(pensionOrganization.getOrganizationId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		uiModel.addAttribute("organization", new PensionOrganization());
		return "pension_organization/create";
	}

	@RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
	public String show(@PathVariable("organizationId") Integer organizationId, Model uiModel) {
		uiModel.addAttribute("organization", pensionOrganizationService.findPensionOrganization(organizationId));
		uiModel.addAttribute("itemId", organizationId);
		return "pension_organization/show";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
			uiModel.addAttribute(
					"organizations",
					pensionOrganizationService.findPensionOrganizationEntries(page == null ? 0 : (page.intValue() - 1)
							* sizeNo, sizeNo));
			float nrOfPages = (float) pensionOrganizationService.countPensionOrganizations() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("organizations", pensionOrganizationService.findAllPensionOrganizations());
		}
		return "pension_organization/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid PensionOrganization pensionOrganization, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("organization", pensionOrganization);
			return "pension_organization/update";
		}
		try {
			pensionOrganizationService.updatePensionOrganization(pensionOrganization);
		} catch (Exception e) {
			uiModel.addAttribute("organization", pensionOrganization);
			addError(uiModel, e);
			return "pension_organization/update";
		}
		uiModel.asMap().clear();
		return "redirect:/pension_organization/"
				+ WebUtil.encodeUrlPathSegment(pensionOrganization.getOrganizationId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{organizationId}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("organizationId") Integer organizationId, Model uiModel) {
		uiModel.addAttribute("organization", pensionOrganizationService.findPensionOrganization(organizationId));
		return "pension_organization/update";
	}

	@RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("organizationId") Integer organizationId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		pensionOrganizationService.deletePensionOrganization(organizationId);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? DEFAULT_PAGE_SIZE : size.toString());
		return "redirect:/pension_organization";
	}

	private void addError(Model uiModel, Throwable e) {
		String errorMessage = e.getLocalizedMessage() + " <br/> ";
		while (e != e.getCause() && e.getCause() != null) {
			e = e.getCause();
			errorMessage += e.getLocalizedMessage() + " <br/> ";
		}
		uiModel.addAttribute("error", errorMessage);
	}

}
