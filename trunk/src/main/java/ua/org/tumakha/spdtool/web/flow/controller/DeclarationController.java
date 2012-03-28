package ua.org.tumakha.spdtool.web.flow.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.RequestContextHolder;

import ua.org.tumakha.spdtool.entity.Group;
import ua.org.tumakha.spdtool.services.GroupService;
import ua.org.tumakha.spdtool.web.flow.model.DeclarationModel;

/**
 * @author Yuriy Tumakha
 */
@Controller
public class DeclarationController {

	private static final Integer DEFAULT_GROUP_ID = 1;

	@Autowired
	private GroupService groupService;

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

}
