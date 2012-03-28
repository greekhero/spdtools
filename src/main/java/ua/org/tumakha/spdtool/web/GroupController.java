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
import ua.org.tumakha.spdtool.entity.Group;
import ua.org.tumakha.spdtool.services.GroupService;
import ua.org.tumakha.spdtool.web.util.WebUtil;

/**
 * @author Yuriy Tumakha
 */
@RequestMapping("/groups")
@Controller
public class GroupController implements AppConfig {

	@Autowired
	private GroupService groupService;

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Group group, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("group", group);
			return "groups/create";
		}
		uiModel.asMap().clear();
		groupService.createGroup(group);
		return "redirect:/groups/"
				+ WebUtil.encodeUrlPathSegment(group.getGroupId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		uiModel.addAttribute("group", new Group());
		return "groups/create";
	}

	@RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
	public String show(@PathVariable("groupId") Integer groupId, Model uiModel) {
		uiModel.addAttribute("group", groupService.findGroup(groupId));
		uiModel.addAttribute("itemId", groupId);
		return "groups/show";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
			uiModel.addAttribute("groups", groupService.findGroupEntries(
					page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
			float nrOfPages = (float) groupService.countGroups() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("groups", groupService.findAllGroups());
		}
		return "groups/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid Group group, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("group", group);
			return "groups/update";
		}
		uiModel.asMap().clear();
		groupService.updateGroup(group);
		return "redirect:/groups/"
				+ WebUtil.encodeUrlPathSegment(group.getGroupId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{groupId}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("groupId") Integer groupId,
			Model uiModel) {
		uiModel.addAttribute("group", groupService.findGroup(groupId));
		return "groups/update";
	}

	@RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("groupId") Integer groupId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		groupService.deleteGroup(groupId);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size",
				(size == null) ? DEFAULT_PAGE_SIZE : size.toString());
		return "redirect:/groups";
	}

}
