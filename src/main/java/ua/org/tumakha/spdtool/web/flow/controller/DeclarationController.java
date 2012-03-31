package ua.org.tumakha.spdtool.web.flow.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AutoPopulatingList;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.core.collection.ParameterMap;
import org.springframework.webflow.execution.RequestContextHolder;

import ua.org.tumakha.spdtool.entity.Declaration;
import ua.org.tumakha.spdtool.entity.Group;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.DeclarationService;
import ua.org.tumakha.spdtool.services.GroupService;
import ua.org.tumakha.spdtool.services.UserService;
import ua.org.tumakha.spdtool.web.flow.model.DeclarationModel;

/**
 * @author Yuriy Tumakha
 */
@Controller
public class DeclarationController {

	private static final Integer DEFAULT_GROUP_ID = 1;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private DeclarationService declarationService;

	public DeclarationModel initModel() {
		initLists();

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
		return declarationModel;
	}

	private void initLists() {
		MutableAttributeMap flowScope = RequestContextHolder
				.getRequestContext().getFlowScope();
		flowScope.put("groups", getGroups());
		flowScope.put("years", getYears());
		flowScope.put("quarters", getQuarters());
	}

	private List<Group> getGroups() {
		return groupService.findAllGroups();
	}

	private List<Integer> getYears() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<Integer> years = new ArrayList<Integer>();
		for (int y = year - 2; y <= year + 1; y++) {
			years.add(y);
		}
		return years;
	}

	private List<Integer> getQuarters() {
		List<Integer> quarters = new ArrayList<Integer>();
		for (int q = 1; q <= 4; q++) {
			quarters.add(q);
		}
		return quarters;
	}

	public void readData(DeclarationModel declarationModel) {
		declarationModel.processFile();
	}

	public void initDeclarations(DeclarationModel declarationModel) {
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

	public void generateDocuments(DeclarationModel declarationModel) {
		System.out.println(declarationModel.getGroupIds());
		System.out.println(declarationModel.getYear());
		System.out.println(declarationModel.getQuarter());
		System.out.println(declarationModel.getDeclarations());
		ParameterMap requestParameters = RequestContextHolder
				.getRequestContext().getRequestParameters();
		List<Declaration> declarations = (List<Declaration>) RequestContextHolder
				.getRequestContext()
				.getRequestScope()
				.getCollection("declarations",
						new ArrayList<Declaration>().getClass());
		MutableAttributeMap flowScope = RequestContextHolder
				.getRequestContext().getFlowScope();
		flowScope.put(
				"declarationsDump",
				requestParameters.toString()
						+ declarations
						+ new ArrayList<Declaration>(declarationModel
								.getDeclarations()));

	}
}
