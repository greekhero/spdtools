package ua.org.tumakha.spdtool.web;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.org.tumakha.spdtool.AppConfig;
import ua.org.tumakha.spdtool.entity.*;
import ua.org.tumakha.spdtool.services.*;
import ua.org.tumakha.spdtool.web.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Yuriy Tumakha
 */
@RequestMapping("/users")
@SessionAttributes("sortMode")
@Controller
public class UserController implements AppConfig {

	@Autowired
	private GroupService groupService;

	@Autowired
	private Kved2010Service kved2010Service;

	@Autowired
	private KvedService kvedService;

	@Autowired
	private UserService userService;

	@Autowired
	private PensionOrganizationService pensionOrganizationService;

	@Autowired
	private TaxOrganizationService taxOrganizationService;

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid User user, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("user", user);
			addDateTimeFormatPatterns(uiModel);
			return "users/create";
		}
		try {
			userService.createUser(user);
		} catch (Exception e) {
			uiModel.addAttribute("user", user);
			addError(uiModel, e);
			return "users/create";
		}
		uiModel.asMap().clear();
		return "redirect:/users/" + WebUtil.encodeUrlPathSegment(user.getUserId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		uiModel.addAttribute("user", new User());
		addDateTimeFormatPatterns(uiModel);
		return "users/create";
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String show(@PathVariable("userId") Integer userId, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("user", userService.findUser(userId));
		uiModel.addAttribute("itemId", userId);
		return "users/show";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortMode", required = false) Integer sortMode, Model uiModel) {
        if (sortMode != null) {
            uiModel.addAttribute("sortMode", sortMode);
        } else {
            sortMode = uiModel.containsAttribute("sortMode") ? (Integer) uiModel.asMap().get("sortMode") : 0;
        }
        List<User> users = null;
        if (page != null || size != null) {
			int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
			users = userService.findUserEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo);
			float nrOfPages = (float) userService.countUsers() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
            users = userService.findAllUsers();
		}

        // Sorting
        String sortProperty = sortMode < 2 ? "userId" : "lastname";
        boolean ascending = sortMode % 2 == 0;
        Comparator<User> comparator = new PropertyComparator(sortProperty, true, ascending);
        Collections.sort(users, comparator);
        uiModel.addAttribute("users", users);

		addDateTimeFormatPatterns(uiModel);
		return "users/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid User user, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("user", user);
			addDateTimeFormatPatterns(uiModel);
			return "users/update";
		}
		try {
			userService.updateUser(user);
		} catch (Exception e) {
			uiModel.addAttribute("user", user);
			addError(uiModel, e);
			return "users/update";
		}
		uiModel.asMap().clear();
		return "redirect:/users/" + WebUtil.encodeUrlPathSegment(user.getUserId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{userId}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("userId") Integer userId, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("user", userService.findUser(userId));
		return "users/update";
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("userId") Integer userId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		userService.deleteUser(userId);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? DEFAULT_PAGE_SIZE : size.toString());
		return "redirect:/users";
	}

	@ModelAttribute("groups")
	public Collection<Group> populateGroups() {
		return groupService.findAllGroups();
	}

	@ModelAttribute("kveds2010")
	public Collection<Kved2010> populateKveds2010() {
		return kved2010Service.findAllKveds();
	}

	@ModelAttribute("kveds")
	public Collection<Kved> populateKveds() {
		return kvedService.findAllKveds();
	}

	private void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("user_regdate_date_format",
				DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("user_regdatedpi_date_format",
				DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
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

	@ModelAttribute("taxOrganizations")
	public Collection<TaxOrganization> populateTaxOrganizations() {
		return taxOrganizationService.findAllTaxOrganizations();
	}

}
