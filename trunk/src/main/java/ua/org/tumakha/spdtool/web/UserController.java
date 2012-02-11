package ua.org.tumakha.spdtool.web;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ua.org.tumakha.spdtool.AppConfig;
import ua.org.tumakha.spdtool.entity.Act;
import ua.org.tumakha.spdtool.entity.Address;
import ua.org.tumakha.spdtool.entity.Bank;
import ua.org.tumakha.spdtool.entity.Contract;
import ua.org.tumakha.spdtool.entity.Group;
import ua.org.tumakha.spdtool.entity.Kved;
import ua.org.tumakha.spdtool.entity.ServiceType;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.KvedService;
import ua.org.tumakha.spdtool.services.UserService;

@RequestMapping("/users")
@Controller
public class UserController implements AppConfig {

	private KvedService kvedService;
	private UserService userService;

	@Required
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Required
	@Autowired
	public void setKvedService(KvedService kvedService) {
		this.kvedService = kvedService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid User user, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("user", user);
			addDateTimeFormatPatterns(uiModel);
			return "users/create";
		}
		uiModel.asMap().clear();
		userService.createUser(user);
		return "redirect:/users/"
				+ encodeUrlPathSegment(user.getUserId().toString(),
						httpServletRequest);
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
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
			uiModel.addAttribute("users", userService.findUserEntries(
					page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
			float nrOfPages = (float) userService.countUsers() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("users", userService.findAllUsers());
		}
		addDateTimeFormatPatterns(uiModel);
		return "users/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid User user, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("user", user);
			addDateTimeFormatPatterns(uiModel);
			return "users/update";
		}
		uiModel.asMap().clear();
		userService.updateUser(user);
		return "redirect:/users/"
				+ encodeUrlPathSegment(user.getUserId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{userId}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("userId") Integer userId,
			Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("user", userService.findUser(userId));
		return "users/update";
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("userId") Integer userId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		userService.deleteUser(userId);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size",
				(size == null) ? DEFAULT_PAGE_SIZE : size.toString());
		return "redirect:/users";
	}

	@ModelAttribute("acts")
	public Collection<Act> populateActs() {
		// return Act.findAllActs();
		return null;
	}

	@ModelAttribute("addresses")
	public Collection<Address> populateAddresses() {
		// return Address.findAllAddresses();
		return null;
	}

	@ModelAttribute("banks")
	public Collection<Bank> populateBanks() {
		// return Bank.findAllBanks();
		return null;
	}

	@ModelAttribute("contracts")
	public Collection<Contract> populateContracts() {
		// return Contract.findAllContracts();
		return null;
	}

	@ModelAttribute("kveds")
	public Collection<Kved> populateKveds() {
		return kvedService.findAllKveds();
	}

	@ModelAttribute("servicetypes")
	public Collection<ServiceType> populateServiceTypes() {
		// return ServiceType.findAllServiceTypes();
		return null;
	}

	@ModelAttribute("users")
	public Collection<User> populateUsers() {
		// return User.findAllUsers();
		return null;
	}

	@ModelAttribute("groups")
	public Collection<Group> populateUserGroups() {
		// return UserGroup.findAllUserGroups();
		return null;
	}

	private void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"user_regdate_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"user_regdatedpi_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	private String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}

}
