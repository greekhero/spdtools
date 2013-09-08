package ua.org.tumakha.spdtool.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.org.tumakha.spdtool.entity.Group;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.GroupService;
import ua.org.tumakha.spdtool.services.TemplateService;
import ua.org.tumakha.spdtool.services.UserService;
import ua.org.tumakha.spdtool.web.model.EcpModel;

/**
 * @author Yuriy Tumakha
 */
@RequestMapping(EcpController.BASE_PATH)
@SessionAttributes(EcpController.MODEL_ATTRIBUTE)
@Controller
public class EcpController {

	private static final Log log = LogFactory.getLog(EcpController.class);

	protected static final String BASE_PATH = "/ecp";

	protected static final String MODEL_ATTRIBUTE = "ecpModel";

	private static final Integer DEFAULT_GROUP_ID = 1;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private TemplateService templateService;

	@RequestMapping(method = RequestMethod.GET)
	public String initData(Model uiModel) {
		Set<Integer> defaultGroupIds = new HashSet<Integer>(Arrays.asList(DEFAULT_GROUP_ID));
		EcpModel ecpModel = new EcpModel();
		ecpModel.setGroupIds(defaultGroupIds);
		ecpModel.setDate(new Date());
		uiModel.addAttribute(MODEL_ATTRIBUTE, ecpModel);
		return redirect("initData");
	}

	@ModelAttribute("groups")
	public Collection<Group> populateGroups() {
		return groupService.findAllGroups();
	}

	@ModelAttribute("dateFormat")
	public String getDateFormat() {
		return DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale());
	}

	@RequestMapping(value = "/{vievName}", method = RequestMethod.GET)
	public String displayView(@PathVariable("vievName") String vievName, Model uiModel) {
		if (!uiModel.containsAttribute(MODEL_ATTRIBUTE)) {
			return redirect("");
		}
		return view(vievName);
	}

	@RequestMapping(value = "/readData", method = RequestMethod.POST)
	public String readData(@Valid EcpModel ecpModel, Model uiModel, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			uiModel.addAttribute(MODEL_ATTRIBUTE, ecpModel);
			return view("initData");
		}

		if (bindingResult.hasErrors()) {
			return view("initData");
		}

		List<User> activeUsers = userService.findActiveUsersByGroups(ecpModel.getGroupIds());
		ecpModel.getEnabledUserIds().clear();
		if (activeUsers != null) {
			for (User user : activeUsers) {
				ecpModel.getEnabledUserIds().add(user.getUserId());
			}
		}
		ecpModel.setUsers(activeUsers);
		uiModel.addAttribute(MODEL_ATTRIBUTE, ecpModel);
		return redirect("users");
	}

	@RequestMapping(value = "/generateDocuments", method = RequestMethod.POST)
	public String generateDocuments(@Valid EcpModel ecpModel,
			@RequestParam(value = "cancel", required = false) String cancel, Model uiModel,
			BindingResult bindingResult, RedirectAttributes redirectAttrs) throws Exception {

		if (cancel != null) {
			return redirect("initData");
		}
		if (ecpModel.getEnabledUserIds() == null) {
			bindingResult.reject("error_users_not_selected");
		}
		if (bindingResult.hasErrors()) {
			return redirect("users");
		}

		List<String> fileNames = templateService.generateEcpDocuments(ecpModel.getEnabledUserIds(),
				ecpModel.getGroupIds(), ecpModel.getDate());
		log.debug("Generated files: " + fileNames.size());

		redirectAttrs.addFlashAttribute("fileNames", fileNames);

		return redirect("downloadDocuments");
	}

	private String view(String viewName) {
		return BASE_PATH.substring(1) + "/" + (viewName == null ? "" : viewName);
	}

	private String redirect(String viewName) {
		return String.format("redirect:%s/%s", BASE_PATH, viewName);
	}

}
