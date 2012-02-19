package ua.org.tumakha.spdtool.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.org.tumakha.spdtool.AppConfig;
import ua.org.tumakha.spdtool.entity.Kved;
import ua.org.tumakha.spdtool.services.KvedService;
import ua.org.tumakha.spdtool.web.util.WebUtil;

@RequestMapping("/kveds")
@Controller
public class KvedController implements AppConfig {

	private KvedService kvedService;

	@Required
	@Autowired
	public void setKvedService(KvedService kvedService) {
		this.kvedService = kvedService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Kved kved, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("kved", kved);
			return "kveds/create";
		}
		uiModel.asMap().clear();
		kvedService.createKved(kved);
		return "redirect:/kveds/"
				+ WebUtil.encodeUrlPathSegment(kved.getKvedId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		uiModel.addAttribute("kved", new Kved());
		return "kveds/create";
	}

	@RequestMapping(value = "/{kvedId}", method = RequestMethod.GET)
	public String show(@PathVariable("kvedId") Integer kvedId, Model uiModel) {
		uiModel.addAttribute("kved", kvedService.findKved(kvedId));
		uiModel.addAttribute("itemId", kvedId);
		return "kveds/show";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
			uiModel.addAttribute("kveds", kvedService.findKvedEntries(
					page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
			float nrOfPages = (float) kvedService.countKveds() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("kveds", kvedService.findAllKveds());
		}
		return "kveds/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid Kved kved, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("kved", kved);
			return "kveds/update";
		}
		uiModel.asMap().clear();
		kvedService.updateKved(kved);
		return "redirect:/kveds/"
				+ WebUtil.encodeUrlPathSegment(kved.getKvedId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{kvedId}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("kvedId") Integer kvedId,
			Model uiModel) {
		uiModel.addAttribute("kved", kvedService.findKved(kvedId));
		return "kveds/update";
	}

	@RequestMapping(value = "/{kvedId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("kvedId") Integer kvedId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		kvedService.deleteKved(kvedId);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size",
				(size == null) ? DEFAULT_PAGE_SIZE : size.toString());
		return "redirect:/kveds";
	}

}
