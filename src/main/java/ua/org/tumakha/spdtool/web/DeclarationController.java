package ua.org.tumakha.spdtool.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.tumakha.spdtool.entity.Declaration;
import ua.org.tumakha.spdtool.entity.Group;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.reader.model.DeclarationReaderModel;
import ua.org.tumakha.spdtool.services.DeclarationService;
import ua.org.tumakha.spdtool.services.GroupService;
import ua.org.tumakha.spdtool.services.TemplateService;
import ua.org.tumakha.spdtool.services.UserService;
import ua.org.tumakha.spdtool.web.model.DeclarationModel;

import javax.validation.Valid;
import java.util.*;

/**
 * @author Yuriy Tumakha
 */
@RequestMapping(DeclarationController.BASE_PATH)
@SessionAttributes("declarationModel")
@Controller
public class DeclarationController {

	private static final Log log = LogFactory.getLog(DeclarationController.class);

	protected static final String BASE_PATH = "/declarations";

	private static final Integer DEFAULT_GROUP_ID = 1;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private DeclarationService declarationService;

	@Autowired
	private TemplateService templateService;

	@RequestMapping(method = RequestMethod.GET)
	public String initData(Model uiModel) {
		Set<Integer> defaultGroupIds = new HashSet<Integer>(Arrays.asList(DEFAULT_GROUP_ID));
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
		return redirect("initData");
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
	public String displayView(@PathVariable("vievName") String vievName, Model uiModel) {
		if (!uiModel.containsAttribute("declarationModel")) {
			return redirect("");
		}
		return view(vievName);
	}

	@RequestMapping(value = "/readData", method = RequestMethod.POST)
	public String readData(@Valid DeclarationModel declarationModel, Model uiModel, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("declarationModel", declarationModel);
			return view("initData");
		}
		List<DeclarationReaderModel> xlsDeclarations = null;
		try {
			xlsDeclarations = declarationModel.processFile(bindingResult);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
		Map<Integer, DeclarationReaderModel> fileDeclarations = getFileDeclarations(xlsDeclarations, bindingResult);
		if (bindingResult.hasErrors()) {
			return view("initData");
		}

		List<User> activeUsers = userService.findActiveUsersByGroups(declarationModel.getGroupIds());
		List<Declaration> declarations = new ArrayList<Declaration>();
		declarationModel.getEnabledUserIds().clear();
		if (activeUsers != null) {
			Map<Integer, Declaration> dbDeclarations = getDbDeclarations(declarationModel);
			for (User user : activeUsers) {
				Declaration declaration;
				if (dbDeclarations.containsKey(user.getUserId())) {
					declaration = dbDeclarations.get(user.getUserId());
				} else {
					declaration = createDeclaration(user, declarationModel);
				}
				if (fileDeclarations != null) {
					DeclarationReaderModel fileDeclaration = fileDeclarations.get(user.getUserId());
					if (fileDeclaration != null) {
						declaration.setIncome(fileDeclaration.getIncome());
						declaration.setTax(fileDeclaration.getTax());
					}
				}
				declarations.add(declaration);
				declarationModel.getEnabledUserIds().add(user.getUserId());
			}
		}
		declarationModel.setDeclarations(declarations);
		uiModel.addAttribute("declarationModel", declarationModel);
		return redirect("usersDeclarations");
	}

	private Map<Integer, DeclarationReaderModel> getFileDeclarations(List<DeclarationReaderModel> xlsDeclarations,
			BindingResult bindingResult) {
		if (xlsDeclarations == null) {
			return null;
		}
		Map<Integer, DeclarationReaderModel> fileDeclarations = new HashMap<Integer, DeclarationReaderModel>();
		log.debug(xlsDeclarations.size() + " rows in xls file.");
		for (DeclarationReaderModel declaration : xlsDeclarations) {
			User user = null;
			try {
				user = userService.findUserByLastname(declaration.getLastname());
			} catch (Exception e) {
				try {
					user = userService.findUserByLastFirst(declaration.getLastname(), declaration.getFirstname());
				} catch (Exception ex) {
					String username = declaration.getLastname() + " " + declaration.getFirstname();
					log.error("User not found" + username, ex);
					bindingResult.reject("error_declaration_user_not_found", new Object[] { username },
							"User not found.");
				}
			}
			if (user != null) {
				fileDeclarations.put(user.getUserId(), declaration);
			}
		}
		return fileDeclarations;
	}

	private Map<Integer, Declaration> getDbDeclarations(DeclarationModel declarationModel) {
		Map<Integer, Declaration> declarationsMap = new HashMap<Integer, Declaration>();
		List<Declaration> dbDeclarations = declarationService.findDeclarationsByYearAndQuarter(
				declarationModel.getYear(), declarationModel.getQuarter());
		for (Declaration declaration : dbDeclarations) {
			declarationsMap.put(declaration.getUser().getUserId(), declaration);
		}
		return declarationsMap;
	}

	private Declaration createDeclaration(User user, DeclarationModel declarationModel) {
		Declaration declaration = new Declaration();
		declaration.setUser(user);
		declaration.setYear(declarationModel.getYear());
		declaration.setQuarter(declarationModel.getQuarter());
		return declaration;
	}

	@RequestMapping(value = "/saveDeclarations", method = RequestMethod.POST)
	public String saveDeclarations(@Valid DeclarationModel declarationModel,
			@RequestParam(value = "cancel", required = false) String cancel, Model uiModel, BindingResult bindingResult)
			throws Exception {
		if (cancel != null) {
			return redirect("initData");
		}
		if (declarationModel.getEnabledUserIds() == null) {
			bindingResult.reject("error_declaration_users_not_selected");
		}
		if (bindingResult.hasErrors()) {
			return redirect("usersDeclarations");
		}
		declarationService.saveDeclarations(declarationModel.getEnabledUserIds(), declarationModel.getDeclarations());

		return redirect("generateDocuments");
	}

	@RequestMapping(value = "/generateDocuments", method = RequestMethod.GET)
	public String generateDocuments(@Valid DeclarationModel declarationModel, RedirectAttributes redirectAttrs)
			throws Exception {

        long time = System.currentTimeMillis();
		List<String> fileNames = templateService.generateDeclarations(declarationModel.getEnabledUserIds(),
				declarationModel.getGroupIds(), declarationModel.getYear(), declarationModel.getQuarter());

		redirectAttrs.addFlashAttribute("fileNames", fileNames);
        redirectAttrs.addFlashAttribute("generationTime", (System.currentTimeMillis() - time) / 1000 );

		return redirect("downloadDocuments");
	}

	private String view(String viewName) {
		return BASE_PATH.substring(1) + "/" + (viewName == null ? "" : viewName);
	}

	private String redirect(String viewName) {
		return String.format("redirect:%s/%s", BASE_PATH, viewName);
	}

}
