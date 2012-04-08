package ua.org.tumakha.spdtool.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
@SessionAttributes("declarationModel")
@Controller
public class DeclarationController {

	private static final Log log = LogFactory
			.getLog(DeclarationController.class);

	private static final Integer DEFAULT_GROUP_ID = 1;

	private static final String START_REDIRECT = "redirect:/declarations";

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
		return "redirect:/declarations/initData";
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
			return START_REDIRECT;
		}
		return "declarations/" + vievName;
	}

	@RequestMapping(value = "/readData", method = RequestMethod.POST)
	public String readData(@Valid DeclarationModel declarationModel,
			Model uiModel, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("declarationModel", declarationModel);
			return "declarations/initData";
		}
		Map<Integer, Declaration> fileDeclarations = null;
		try {
			fileDeclarations = declarationModel.processFile(bindingResult);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
		if (bindingResult.hasErrors()) {
			return "declarations/initData";
		}
		List<User> activeUsers = userService
				.findActiveUsersByGroups(declarationModel.getGroupIds());

		// List<Declaration> declarations = new AutoPopulatingList<Declaration>(
		// Declaration.class);
		List<Declaration> declarations = new ArrayList<Declaration>();
		if (activeUsers != null) {
			Map<Integer, Declaration> dbDeclarations = getDbDeclarations(
					declarationModel.getYear(), declarationModel.getQuarter());
			for (User user : activeUsers) {
				Declaration declaration;
				if (fileDeclarations != null
						&& fileDeclarations.containsKey(user.getUserId())) {
					declaration = fileDeclarations.get(user.getUserId());
				} else if (dbDeclarations.containsKey(user.getUserId())) {
					declaration = dbDeclarations.get(user.getUserId());
				} else {
					declaration = declarationModel.createDeclaration(user,
							null, null);
				}
				declarations.add(declaration);
			}
		}
		declarationModel.setDeclarations(declarations);
		uiModel.addAttribute("declarationModel", declarationModel);
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

	@RequestMapping(value = "/generateDocuments", method = RequestMethod.POST)
	public String generateDocuments(@Valid DeclarationModel declarationModel,
			@RequestParam(value = "cancel", required = false) String cancel,
			Model uiModel, BindingResult bindingResult,
			RedirectAttributes redirectAttrs) {
		if (cancel != null) {
			return "redirect:/declarations/initData";
		}
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("declarationModel", declarationModel);
			return "declarations/userDeclarations";
		}
		declarationService.saveDeclarations(declarationModel.getDeclarations());
		// redirectAttrs.addFlashAttribute("declarationModel",
		// declarationModel);
		// TODO: complete session
		return "redirect:/declarations/downloadDocuments";
	}

}
