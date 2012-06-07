package ua.org.tumakha.spdtool.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import ua.org.tumakha.spdtool.entity.Declaration;
import ua.org.tumakha.spdtool.entity.Kved;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.DeclarationService;
import ua.org.tumakha.spdtool.services.TemplateService;
import ua.org.tumakha.spdtool.services.UserService;
import ua.org.tumakha.spdtool.template.XlsProcessor;
import ua.org.tumakha.spdtool.template.XlsTemplate;
import ua.org.tumakha.spdtool.template.model.ActModel;
import ua.org.tumakha.spdtool.template.model.Form11KvedModel;
import ua.org.tumakha.spdtool.template.model.Form20OPPModel;
import ua.org.tumakha.spdtool.template.model.IncomeCalculationModel;
import ua.org.tumakha.spdtool.template.model.TaxSystemStatementModel;

/**
 * @author Yuriy Tumakha
 */
@Service("templateService")
@Repository
public class TemplateServiceImpl implements TemplateService {

	private static final Log log = LogFactory.getLog(TemplateServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private DeclarationService declarationService;

	private final XlsProcessor xlsProcessor = new XlsProcessor();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ActModel> getActModelList() {
		List<User> users = userService.findActiveUsers();
		if (users != null && users.size() > 0) {
			List<ActModel> listModel = new ArrayList<ActModel>(users.size());
			User lastUser = null;
			try {
				for (User user : users) {
					lastUser = user;
					ActModel actModel = new ActModel(user);
					listModel.add(actModel);

					// } catch (Exception e) {
					// System.out.println(user.getLastname() + ": "
					// + e.getMessage());
					// System.out.println(e);
					// throw new RuntimeException(e);
					// }
				}
			} finally {
				log.debug("Last User: " + lastUser.getUserId() + " "
						+ lastUser.getLastnameEn());
			}

			return listModel;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<TaxSystemStatementModel> getTaxSystemStatementModelList(
			Integer groupId) {
		List<User> users = userService.findUsersByGroup(groupId);
		if (users != null && users.size() > 0) {
			List<TaxSystemStatementModel> listModel = new ArrayList<TaxSystemStatementModel>(
					users.size());
			for (User user : users) {
				if (user.isActive()) {
					TaxSystemStatementModel taxSystemStatementModel = new TaxSystemStatementModel(
							user);
					listModel.add(taxSystemStatementModel);
				}
			}
			return listModel;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<IncomeCalculationModel> getIncomeCalculationModelList(
			Integer groupId) {
		List<User> users = userService.findUsersByGroup(groupId);
		if (users != null && users.size() > 0) {
			List<IncomeCalculationModel> listModel = new ArrayList<IncomeCalculationModel>(
					users.size());
			for (User user : users) {
				if (user.isActive()) {
					IncomeCalculationModel model = new IncomeCalculationModel(
							user);
					listModel.add(model);
				}
			}
			return listModel;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Form20OPPModel> getForm20OPPModelList() {
		List<User> users = userService.findActiveUsers();
		if (users != null && users.size() > 0) {
			List<Form20OPPModel> listModel = new ArrayList<Form20OPPModel>(
					users.size());
			for (User user : users) {
				Form20OPPModel form20OPPModel = new Form20OPPModel(user);
				listModel.add(form20OPPModel);
			}
			return listModel;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Form11KvedModel> getForm11KvedModelList(Integer groupId) {
		List<User> users = userService.findUsersByGroup(groupId);
		if (users != null && users.size() > 0) {
			List<Form11KvedModel> listModel = new ArrayList<Form11KvedModel>(
					users.size());
			for (User user : users) {
				if (user.isActive()) {
					Form11KvedModel form11Model = new Form11KvedModel(user);
					listModel.add(form11Model);
				}
			}
			return listModel;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> getUsersForDeclaration(Integer groupId) {
		List<User> users = userService.findUsersByGroup(groupId);
		List<User> usersForDeclaration = new ArrayList<User>();
		if (users != null && users.size() > 0) {
			for (User user : users) {
				if (user.isActive() && user.getDeclarations() != null
						&& user.getDeclarations().size() > 0) {
					user.getActiveKveds().size();
					usersForDeclaration.add(user);
				}
			}
			return usersForDeclaration;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> getUsersForDeclaration(List<Integer> groupIds) {
		Assert.notNull(groupIds, "groupIds empty.");
		List<User> users = userService.findUsersByGroups(groupIds);
		List<User> usersForDeclaration = new ArrayList<User>();
		if (users != null && users.size() > 0) {
			for (User user : users) {
				if (user.isActive()) {
					user.getDeclarations().size();
					user.getActiveKveds().size();
					usersForDeclaration.add(user);
				}
			}
			return usersForDeclaration;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> generateDeclarations(Set<Integer> groupIds,
			Integer year, Integer quarter) throws InvalidFormatException,
			IOException {
		char[] dateYear = ("" + (quarter == 4 ? year + 1 : year)).toCharArray();
		List<String> fileNames = new ArrayList<String>();
		// TODO filter by groupIds
		List<Declaration> declarations = declarationService
				.findDeclarationsByYearAndQuarter(year, quarter);
		for (Declaration declaration : declarations) {
			User user = declaration.getUser();
			Integer income = declaration.getIncome();
			Integer tax = declaration.getTax();
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("user", user);
			beans.put("year", year);
			beans.put("dateYear", dateYear);
			beans.put("income", income);
			beans.put("tax", tax);
			// TODO: if quarter != 1 get previous tax from DB.
			beans.put("previousTax", "-");
			beans.put("taxToPay", tax);
			beans.put("phone0", "0976884343");
			for (int q = 1; q <= 4; q++) {
				String qsym = q == quarter ? "X" : "";
				beans.put("q" + q, qsym);
			}
			List<Kved> kveds = user.getActiveKveds();
			for (int k = 1; k <= 6; k++) {
				String code = "";
				String name = "";
				if (kveds.size() >= k) {
					Kved kved = kveds.get(k - 1);
					code = kved.getCode();
					name = kved.getName();
				}
				beans.put("kvedCode" + k, code);
				beans.put("kvedName" + k, name);
			}
			String outputFilenamePrefix = String.format(
					"/DECLARATION/%d_Q%d/%s_%s_%d_Q%d_", year, quarter,
					user.getLastnameEn(), user.getFirstnameEn(), year, quarter);
			String outputFilename = xlsProcessor.saveReport(
					XlsTemplate.DECLARATION, outputFilenamePrefix, beans);
			fileNames.add(outputFilename);
		}
		return fileNames;
	}

}