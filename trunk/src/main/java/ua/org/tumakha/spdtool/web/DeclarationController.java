package ua.org.tumakha.spdtool.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.org.tumakha.spdtool.entity.Declaration;
import ua.org.tumakha.spdtool.entity.Group;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.DeclarationService;
import ua.org.tumakha.spdtool.services.GroupService;
import ua.org.tumakha.spdtool.services.UserService;
import ua.org.tumakha.spdtool.web.model.DeclarationModel;

/**
 * @author Yuriy Tumakha
 */
@RequestMapping("/declarations")
@Controller
public class DeclarationController {

	private static final Integer DEFAULT_GROUP_ID = 1;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private DeclarationService declarationService;

	@RequestMapping(method = RequestMethod.GET)
	public String initData(Model uiModel) {
		Set<Integer> defaultGroupIds = new HashSet<Integer>(
				Arrays.asList(DEFAULT_GROUP_ID));
		Calendar calendar = Calendar.getInstance();
		int defaultYear = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		if (month == 0) {
			defaultYear = defaultYear - 1;
		}
		int defaultQuarter = month == 0 ? 4 : (month + 1) / 3;

		DeclarationModel declarationModel = new DeclarationModel();
		declarationModel.setGroupIds(defaultGroupIds);
		declarationModel.setYear(defaultYear);
		declarationModel.setQuarter(defaultQuarter);
		uiModel.addAttribute("declarationModel", declarationModel);
		return "declarations/initData";
	}

	@ModelAttribute("groups")
	public Collection<Group> populateGroups() {
		return groupService.findAllGroups();
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

	@ModelAttribute("quarters")
	public Collection<Integer> getQuarters() {
		List<Integer> quarters = new ArrayList<Integer>();
		for (int q = 1; q <= 4; q++) {
			quarters.add(q);
		}
		return quarters;
	}

	@RequestMapping(value = "/{vievName}", method = RequestMethod.GET)
	public String displayView(@PathVariable("vievName") String vievName,
			Model uiModel) {
		if (!uiModel.containsAttribute("declarationModel")) {
			return "redirect:/declarations";
		}
		return "declarations/" + vievName;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String readData(@Valid DeclarationModel declarationModel,
			Model uiModel, BindingResult bindingResult,
			RedirectAttributes redirectAttrs) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("declarationModel", declarationModel);
			return "declarations/initData";
		}
		declarationModel.processFile();
		List<User> activeUsers = userService
				.findActiveUsersByGroups(declarationModel.getGroupIds());
		List<Declaration> declarations = new AutoPopulatingList<Declaration>(
				Declaration.class);
		if (activeUsers != null) {
			Map<Integer, Declaration> dbDeclarations = getDbDeclarations(
					declarationModel.getYear(), declarationModel.getQuarter());
			for (User user : activeUsers) {
				Declaration declaration;
				if (dbDeclarations.containsKey(user.getUserId())) {
					declaration = dbDeclarations.get(user.getUserId());
				} else {
					declaration = new Declaration();
					declaration.setUser(user);
					declaration.setYear(declarationModel.getYear());
					declaration.setQuarter(declarationModel.getQuarter());
					// declaration.setIncome(100);
					// declaration.setTax(5);
				}
				declarations.add(declaration);
			}
		}
		declarationModel.setDeclarations(declarations);
		redirectAttrs.addFlashAttribute("declarationModel", declarationModel);
		return "redirect:/declarations/usersDeclarations";
	}

	private Map<Integer, Declaration> getDbDeclarations(Integer year,
			Integer quarter) {
		Map<Integer, Declaration> declarationsMap = new HashMap<Integer, Declaration>();
		List<Declaration> dbDeclarations = declarationService
				.findDeclarationsByYearAndQuarter(year, quarter);
		for (Declaration declaration : dbDeclarations) {
			declarationsMap.put(declaration.getUser().getUserId(), declaration);
		}
		return declarationsMap;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String generateDocuments(DeclarationModel declarationModel,
			Model uiModel, BindingResult bindingResult,
			HttpServletRequest httpServletRequest) {
		System.out.println(declarationModel.getGroupIds());
		System.out.println(declarationModel.getYear());
		System.out.println(declarationModel.getQuarter());
		System.out.println(declarationModel.getDeclarations());
		// flowScope.put(
		// "declarationsDump",
		// + new ArrayList<Declaration>(declarationModel
		// .getDeclarations()));
		return "redirect:/declarations/downloadDocuments";
	}

}
