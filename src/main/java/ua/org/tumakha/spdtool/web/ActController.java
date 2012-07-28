package ua.org.tumakha.spdtool.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import ua.org.tumakha.spdtool.entity.Act;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.reader.model.ActReaderModel;
import ua.org.tumakha.spdtool.services.ActService;
import ua.org.tumakha.spdtool.services.TemplateService;
import ua.org.tumakha.spdtool.services.UserService;
import ua.org.tumakha.spdtool.web.model.ActModel;

/**
 * @author Yuriy Tumakha
 */
@RequestMapping(ActController.BASE_PATH)
@SessionAttributes("actModel")
@Controller
public class ActController {

	private static final Log log = LogFactory.getLog(ActController.class);

	protected static final String BASE_PATH = "/acts";

	private static final Integer EXCLUDE_USER_ID = 93;

	@Autowired
	private UserService userService;

	@Autowired
	private ActService actService;

	@Autowired
	private TemplateService templateService;

	@RequestMapping(method = RequestMethod.GET)
	public String initData(Model uiModel) {
		Calendar calendar = Calendar.getInstance();
		int defaultYear = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		if (month == 0) {
			defaultYear = defaultYear - 1;
		}
		int defaultMonth = month == 0 ? 12 : month;

		ActModel actModel = new ActModel();
		actModel.setYear(defaultYear);
		actModel.setMonth(defaultMonth);
		uiModel.addAttribute("actModel", actModel);
		return redirect("initData");
	}

	@ModelAttribute("years")
	public Collection<Integer> getYears() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<Integer> years = new ArrayList<Integer>();
		for (int y = year - 2; y <= year + 1; y++) {
			years.add(y);
		}
		return years;
	}

	@ModelAttribute("months")
	public Collection<Integer> getMonths() {
		List<Integer> months = new ArrayList<Integer>();
		for (int q = 1; q <= 12; q++) {
			months.add(q);
		}
		return months;
	}

	@RequestMapping(value = "/{vievName}", method = RequestMethod.GET)
	public String displayView(@PathVariable("vievName") String vievName,
			Model uiModel) {
		if (!uiModel.containsAttribute("actModel")) {
			return redirect("");
		}
		return view(vievName);
	}

	@RequestMapping(value = "/readData", method = RequestMethod.POST)
	public String readData(@Valid ActModel actModel, Model uiModel,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("actModel", actModel);
			return view("initData");
		}
		List<ActReaderModel> xlsActs = null;
		try {
			xlsActs = actModel.processFile(bindingResult);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
		Map<Integer, ActReaderModel> fileActs = getFileActs(xlsActs,
				bindingResult);
		if (bindingResult.hasErrors()) {
			return view("initData");
		}

		List<User> activeUsers = userService.findActiveUsers();
		List<Act> acts = new ArrayList<Act>();
		if (activeUsers != null) {
			Map<Integer, Act> dbActs = getDbActs(actModel);
			for (User user : activeUsers) {
				if (!user.getUserId().equals(EXCLUDE_USER_ID)) {
					Act act;
					if (dbActs.containsKey(user.getUserId())) {
						act = dbActs.get(user.getUserId());
					} else {
						act = createAct(user, actModel);
					}
					if (fileActs != null) {
						ActReaderModel fileAct = fileActs.get(user.getUserId());
						if (fileAct != null) {
							act.setAmount(fileAct.getSalary());
						}
					}
					acts.add(act);
				}
			}
		}
		actModel.setActs(acts);
		uiModel.addAttribute("actModel", actModel);
		return redirect("usersActs");
	}

	private Map<Integer, ActReaderModel> getFileActs(
			List<ActReaderModel> xlsActs, BindingResult bindingResult) {
		if (xlsActs == null) {
			return null;
		}
		Map<Integer, ActReaderModel> fileActs = new HashMap<Integer, ActReaderModel>();
		log.debug(xlsActs.size() + " rows in xls file.");
		for (ActReaderModel act : xlsActs) {
			try {
				User user = userService.findUserByLastname(act.getLastname());
				fileActs.put(user.getUserId(), act);
			} catch (Exception e) {
				bindingResult.reject("error_act_user_not_found",
						new Object[] { act.getLastname() }, "User not found.");
			}
		}
		return fileActs;
	}

	private Map<Integer, Act> getDbActs(ActModel actModel) {
		Map<Integer, Act> actsMap = new HashMap<Integer, Act>();
		List<Act> dbActs = actService.findActsByYearAndMonth(
				actModel.getYear(), actModel.getMonth());
		for (Act act : dbActs) {
			actsMap.put(act.getUser().getUserId(), act);
		}
		return actsMap;
	}

	private Act createAct(User user, ActModel actModel) {
		Act act = new Act();
		act.setUser(user);
		// act.setYear(actModel.getYear());
		// act.setQuarter(actModel.getMonth());
		return act;
	}

	@RequestMapping(value = "/saveActs", method = RequestMethod.POST)
	public String saveActs(@Valid ActModel actModel,
			@RequestParam(value = "cancel", required = false) String cancel,
			Model uiModel, BindingResult bindingResult,
			RedirectAttributes redirectAttrs) throws Exception {
		if (cancel != null) {
			return redirect("initData");
		}
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("actModel", actModel);
			return view("userActs");
		}
		actService.saveActs(actModel.getActs());

		return redirect("generateDocuments");
	}

	@RequestMapping(value = "/generateDocuments", method = RequestMethod.GET)
	public String generateDocuments(@Valid ActModel actModel,
			RedirectAttributes redirectAttrs) throws Exception {

		// List<String> fileNames = templateService.generateActs(
		// actModel.getYear(), actModel.getMonth());

		// redirectAttrs.addFlashAttribute("fileNames", fileNames);

		return redirect("downloadDocuments");
	}

	private String view(String viewName) {
		return BASE_PATH.substring(1) + "/"
				+ (viewName == null ? "" : viewName);
	}

	private String redirect(String viewName) {
		return String.format("redirect:%s/%s", BASE_PATH, viewName);
	}

}
