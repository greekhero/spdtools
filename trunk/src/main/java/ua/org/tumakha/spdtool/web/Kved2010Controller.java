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
import ua.org.tumakha.spdtool.entity.Kved2010;
import ua.org.tumakha.spdtool.services.Kved2010Service;
import ua.org.tumakha.spdtool.web.util.WebUtil;

@RequestMapping("/kveds2010")
@Controller
public class Kved2010Controller implements AppConfig {

	@Autowired
	private Kved2010Service kved2010Service;

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Kved2010 kved, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("kved", kved);
			return "kveds2010/create";
		}
		uiModel.asMap().clear();
		kved2010Service.createKved(kved);
		return "redirect:/kveds2010/"
				+ WebUtil.encodeUrlPathSegment(kved.getKvedId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		uiModel.addAttribute("kved", new Kved2010());
		return "kveds2010/create";
	}

	@RequestMapping(value = "/{kvedId}", method = RequestMethod.GET)
	public String show(@PathVariable("kvedId") Integer kvedId, Model uiModel) {
		uiModel.addAttribute("kved", kved2010Service.findKved(kvedId));
		uiModel.addAttribute("itemId", kvedId);
		return "kveds2010/show";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
			uiModel.addAttribute("kveds", kved2010Service.findKvedEntries(
					page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
			float nrOfPages = (float) kved2010Service.countKveds() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("kveds", kved2010Service.findAllKveds());
		}
		return "kveds2010/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid Kved2010 kved, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("kved", kved);
			return "kveds2010/update";
		}
		uiModel.asMap().clear();
		kved2010Service.updateKved(kved);
		return "redirect:/kveds2010/"
				+ WebUtil.encodeUrlPathSegment(kved.getKvedId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{kvedId}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("kvedId") Integer kvedId,
			Model uiModel) {
		uiModel.addAttribute("kved", kved2010Service.findKved(kvedId));
		return "kveds2010/update";
	}

	@RequestMapping(value = "/{kvedId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("kvedId") Integer kvedId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		kved2010Service.deleteKved(kvedId);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size",
				(size == null) ? DEFAULT_PAGE_SIZE : size.toString());
		return "redirect:/kveds2010";
	}

}
