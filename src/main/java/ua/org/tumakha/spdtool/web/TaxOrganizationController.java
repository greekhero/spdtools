package ua.org.tumakha.spdtool.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.org.tumakha.spdtool.AppConfig;
import ua.org.tumakha.spdtool.entity.PensionOrganization;
import ua.org.tumakha.spdtool.entity.TaxOrganization;
import ua.org.tumakha.spdtool.services.PensionOrganizationService;
import ua.org.tumakha.spdtool.services.TaxOrganizationService;
import ua.org.tumakha.spdtool.web.util.WebUtil;

/**
 * @author Yuriy Tumakha
 */
@RequestMapping("/tax_organization")
@Controller
public class TaxOrganizationController implements AppConfig {

	@Autowired
	private TaxOrganizationService taxOrganizationService;

	@Autowired
	private PensionOrganizationService pensionOrganizationService;

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid TaxOrganization taxOrganization, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("organization", taxOrganization);
			return "tax_organization/create";
		}
		try {
			taxOrganizationService.createTaxOrganization(taxOrganization);
		} catch (Exception e) {
			uiModel.addAttribute("organization", taxOrganization);
			addError(uiModel, e);
			return "tax_organization/create";
		}
		uiModel.asMap().clear();
		return "redirect:/tax_organization/"
				+ WebUtil.encodeUrlPathSegment(taxOrganization.getOrganizationId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		uiModel.addAttribute("organization", new TaxOrganization());
		return "tax_organization/create";
	}

	@RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
	public String show(@PathVariable("organizationId") Integer organizationId, Model uiModel) {
		uiModel.addAttribute("organization", taxOrganizationService.findTaxOrganization(organizationId));
		uiModel.addAttribute("itemId", organizationId);
		return "tax_organization/show";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
			uiModel.addAttribute("organizations", taxOrganizationService.findTaxOrganizationEntries(page == null ? 0
					: (page.intValue() - 1) * sizeNo, sizeNo));
			float nrOfPages = (float) taxOrganizationService.countTaxOrganizations() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("organizations", taxOrganizationService.findAllTaxOrganizations());
		}
		return "tax_organization/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid TaxOrganization taxOrganization, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("organization", taxOrganization);
			return "tax_organization/update";
		}
		try {
			taxOrganizationService.updateTaxOrganization(taxOrganization);
		} catch (Exception e) {
			uiModel.addAttribute("organization", taxOrganization);
			addError(uiModel, e);
			return "tax_organization/update";
		}
		uiModel.asMap().clear();
		return "redirect:/tax_organization/"
				+ WebUtil.encodeUrlPathSegment(taxOrganization.getOrganizationId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{organizationId}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("organizationId") Integer organizationId, Model uiModel) {
		uiModel.addAttribute("organization", taxOrganizationService.findTaxOrganization(organizationId));
		return "tax_organization/update";
	}

	@RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("organizationId") Integer organizationId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		taxOrganizationService.deleteTaxOrganization(organizationId);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? DEFAULT_PAGE_SIZE : size.toString());
		return "redirect:/tax_organization";
	}

	private void addError(Model uiModel, Throwable e) {
		String errorMessage = e.getLocalizedMessage() + " <br/> ";
		while (e != e.getCause() && e.getCause() != null) {
			e = e.getCause();
			errorMessage += e.getLocalizedMessage() + " <br/> ";
		}
		uiModel.addAttribute("error", errorMessage);
	}

	@ModelAttribute("pensionOrganizations")
	public Collection<PensionOrganization> populatePensionOrganizations() {
		return pensionOrganizationService.findAllPensionOrganizations();
	}

}
