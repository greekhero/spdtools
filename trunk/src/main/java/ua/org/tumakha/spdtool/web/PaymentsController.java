package ua.org.tumakha.spdtool.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import ua.org.tumakha.spdtool.web.model.PaymentModel;

/**
 * @author Yuriy Tumakha
 */
@RequestMapping(PaymentsController.BASE_PATH)
@SessionAttributes(PaymentsController.MODEL_ATTRIBUTE)
@Controller
public class PaymentsController {

	private static final Log log = LogFactory.getLog(PaymentsController.class);

	protected static final String BASE_PATH = "/payments";

	protected static final String MODEL_ATTRIBUTE = "paymentModel";

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
		PaymentModel paymentModel = new PaymentModel();
		paymentModel.setGroupIds(defaultGroupIds);
		uiModel.addAttribute(MODEL_ATTRIBUTE, paymentModel);
		return redirect("initData");
	}

	@ModelAttribute("groups")
	public Collection<Group> populateGroups() {
		return groupService.findAllGroups();
	}

	@RequestMapping(value = "/{vievName}", method = RequestMethod.GET)
	public String displayView(@PathVariable("vievName") String vievName, Model uiModel) {
		if (!uiModel.containsAttribute(MODEL_ATTRIBUTE)) {
			return redirect("");
		}
		return view(vievName);
	}

	@RequestMapping(value = "/readData", method = RequestMethod.POST)
	public String readData(@Valid PaymentModel paymentModel, Model uiModel, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			uiModel.addAttribute(MODEL_ATTRIBUTE, paymentModel);
			return view("initData");
		}

		if (bindingResult.hasErrors()) {
			return view("initData");
		}

		List<User> activeUsers = userService.findActiveUsersByGroups(paymentModel.getGroupIds());
		paymentModel.getEnabledUserIds().clear();
		if (activeUsers != null) {
			for (User user : activeUsers) {
				paymentModel.getEnabledUserIds().add(user.getUserId());
			}
		}
		paymentModel.setUsers(activeUsers);
		uiModel.addAttribute(MODEL_ATTRIBUTE, paymentModel);
		return redirect("users");
	}

	@RequestMapping(value = "/generateDocuments", method = RequestMethod.POST)
	public String generateDocuments(@Valid PaymentModel paymentModel,
			@RequestParam(value = "cancel", required = false) String cancel, Model uiModel,
			BindingResult bindingResult, RedirectAttributes redirectAttrs) throws Exception {

		if (cancel != null) {
			return redirect("initData");
		}
		if (paymentModel.getEnabledUserIds() == null) {
			bindingResult.reject("error_users_not_selected");
		}
		if (bindingResult.hasErrors()) {
			return redirect("users");
		}

		log.debug("EnabledUserIds: " + paymentModel.getEnabledUserIds());
		List<String> fileNames = templateService.generatePaymentDocuments(paymentModel.getEnabledUserIds(),
				paymentModel.isSendEmail());

		redirectAttrs.addFlashAttribute("fileNames", fileNames);

		return redirect("generatedDocuments");
	}

	private String view(String viewName) {
		return BASE_PATH.substring(1) + "/" + (viewName == null ? "" : viewName);
	}

	private String redirect(String viewName) {
		return String.format("redirect:%s/%s", BASE_PATH, viewName);
	}

}
