package ua.org.tumakha.spdtool.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import ua.org.tumakha.spdtool.entity.Contract;
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
	public String displayView(@PathVariable("vievName") String vievName, Model uiModel) {
		if (!uiModel.containsAttribute("actModel")) {
			return redirect("");
		}
		return view(vievName);
	}

	@RequestMapping(value = "/readData", method = RequestMethod.POST)
	public String readData(@Valid ActModel actModel, Model uiModel, BindingResult bindingResult) {
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
		Map<Integer, ActReaderModel> fileActs = getFileActs(xlsActs, bindingResult);
		if (bindingResult.hasErrors()) {
			return view("initData");
		}

		List<User> activeUsers = userService.findActiveUsers();
		List<Act> acts = new ArrayList<Act>();
		actModel.getEnabledUserIds().clear();
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
					actModel.getEnabledUserIds().add(user.getUserId());
				}
			}
		}
		actModel.setActs(acts);
		uiModel.addAttribute("actModel", actModel);
		return redirect("usersActs");
	}

	private Map<Integer, ActReaderModel> getFileActs(List<ActReaderModel> xlsActs, BindingResult bindingResult) {
		if (xlsActs == null) {
			return null;
		}
		Map<Integer, ActReaderModel> fileActs = new HashMap<Integer, ActReaderModel>();
		log.debug(xlsActs.size() + " rows in xls file.");
		for (ActReaderModel act : xlsActs) {
			User user = null;
			try {
				user = userService.findUserByLastname(act.getLastname());
			} catch (Exception e) {
				try {
					user = userService.findUserByLastFirst(act.getLastname(), act.getFirstname());
				} catch (Exception ex) {
					String actUsername = act.getLastname() + " " + act.getFirstname();
					log.error("User not found" + actUsername, ex);
					bindingResult.reject("error_act_user_not_found", new Object[] { actUsername }, "User not found.");
				}
			}
			if (user != null) {
				fileActs.put(user.getUserId(), act);
			}
		}
		return fileActs;
	}

	private Map<Integer, Act> getDbActs(ActModel actModel) {
		Map<Integer, Act> actsMap = new HashMap<Integer, Act>();
		List<Act> dbActs = actService.findActsByYearAndMonth(actModel.getYear(), actModel.getMonth());
		for (Act act : dbActs) {
			actsMap.put(act.getUser().getUserId(), act);
		}
		return actsMap;
	}

	private Act createAct(User user, ActModel actModel) {
		Contract lastContract = user.getLastContract();
		Act lastAct = null;
		if (lastContract == null) {
			lastContract = createContract(user, actModel);
		} else {
			Calendar calendarLastContract = Calendar.getInstance();
			calendarLastContract.setTime(lastContract.getDate());
			if (!actModel.getYear().equals(calendarLastContract.get(Calendar.YEAR))) {
				lastContract = createContract(user, actModel);
			} else {
				lastAct = user.getLastAct();
			}
		}

		String actNumber = lastContract.getNumber() + "-01";
		if (lastAct != null) {
			Matcher matcher = Pattern.compile("(\\d+)$").matcher(lastAct.getNumber());
			if (matcher.find()) {
				StringBuffer result = new StringBuffer();
				matcher.appendReplacement(result, String.format("%02d", Integer.parseInt(matcher.group(1)) + 1));
				actNumber = result.toString();
			}
		}
		Calendar calendarActFrom = new GregorianCalendar(actModel.getYear(), actModel.getMonth() - 1, 1);
		int nextMonth = actModel.getMonth() == 12 ? 0 : actModel.getMonth();
		int nextYear = actModel.getMonth() == 12 ? actModel.getYear() + 1 : actModel.getYear();
		Calendar calendarActTo = new GregorianCalendar(nextYear, nextMonth, 1);
		calendarActTo.add(Calendar.DAY_OF_MONTH, -1);

		Act act = new Act();
		act.setUser(user);
		act.setContract(lastContract);
		act.setNumber(actNumber);
		act.setDateFrom(calendarActFrom.getTime());
		act.setDateTo(calendarActTo.getTime());
		return act;
	}

	private Contract createContract(User user, ActModel actModel) {
		Calendar calendarContract = new GregorianCalendar(actModel.getYear(), actModel.getMonth() - 1, 1);
		String contractNumber = String.format("%s%s%s/%d-%02d", user.getFirstnameEn().charAt(0), user.getMiddlenameEn()
				.charAt(0), user.getLastnameEn().charAt(0), actModel.getYear(), actModel.getMonth());

		Contract contract = new Contract();
		contract.setUser(user);
		contract.setDate(calendarContract.getTime());
		contract.setNumber(contractNumber);
		return contract;
	}

	@RequestMapping(value = "/saveActs", method = RequestMethod.POST)
	public String saveActs(@Valid ActModel actModel, @RequestParam(value = "cancel", required = false) String cancel,
			Model uiModel, BindingResult bindingResult, RedirectAttributes redirectAttrs) throws Exception {
		if (cancel != null) {
			return redirect("initData");
		}
		if (actModel.getEnabledUserIds() == null) {
			bindingResult.reject("error_act_users_not_selected");
		}
		if (bindingResult.hasErrors()) {
			return redirect("usersActs");
		}
		actService.saveActs(actModel.getActs(), actModel.getEnabledUserIds());

		return redirect("generateDocuments");
	}

	@RequestMapping(value = "/generateDocuments", method = RequestMethod.GET)
	public String generateDocuments(@Valid ActModel actModel, RedirectAttributes redirectAttrs) throws Exception {

		List<String> fileNames = templateService.generateActs(actModel.getEnabledUserIds(), actModel.getYear(),
				actModel.getMonth(), actModel.isGenerateContracts(), actModel.isGenerateActs());

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
