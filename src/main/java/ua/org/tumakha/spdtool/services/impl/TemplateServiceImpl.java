package ua.org.tumakha.spdtool.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.TemplateService;
import ua.org.tumakha.spdtool.services.UserService;
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
	private UserService userService;

	@Required
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

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
					user.getKveds().size();
					usersForDeclaration.add(user);
				}
			}
			return usersForDeclaration;
		}
		return null;
	}

}