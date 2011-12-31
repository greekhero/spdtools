import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.services.UserService;
import ua.org.tumakha.spd.template.DocxTemplate;
import ua.org.tumakha.spd.template.DocxTemplate.Template;
import ua.org.tumakha.spd.template.model.ActModel;
import ua.org.tumakha.spd.template.model.Form20OPPModel;

/**
 * @author Yuriy Tumakha
 */
public class GenerateReportsTest {

	private static String[] CONFIG_LOCATIONS = {
			"classpath:datasource-test.xml", "classpath:persistenceContext.xml" };
	private static ApplicationContext applicationContext;
	private static UserService userService;
	private final DocxTemplate docxTemplate = new DocxTemplate();

	@BeforeClass
	public static void init() {
		applicationContext = new ClassPathXmlApplicationContext(
				CONFIG_LOCATIONS);
		userService = (UserService) applicationContext.getBean("userService");

	}

	@Test
	public void testGenerateReports() throws Exception {
		List<ActModel> listModel = getActModelList();
		docxTemplate.saveReports(Template.ACT, listModel);
		// docxTemplate.saveReports(Template.CONTRACT_AMENDMENT, listModel);
		if (listModel != null) {
			System.out.println("Generated report models: " + listModel.size());
		}
	}

	@Test
	public void testGenerateForm20OPP() throws Exception {
		List<Form20OPPModel> listModel = getForm20OPPModelList();
		docxTemplate.saveReports(Template.FORM_20_OPP, listModel);
		if (listModel != null) {
			System.out.println("Generated 20-OPP forms: " + listModel.size());
		}
	}

	private List<ActModel> getActModelList() {
		List<User> users = userService.findActiveUsers();
		if (users != null && users.size() > 0) {
			List<ActModel> listModel = new ArrayList<ActModel>(users.size());
			for (User user : users) {
				if (user.getActs().size() == 1) {
					ActModel actModel = new ActModel(user);
					listModel.add(actModel);
				}
			}
			return listModel;
		}
		return null;
	}

	private List<Form20OPPModel> getForm20OPPModelList() {
		List<User> users = userService.findActiveUsers();
		if (users != null && users.size() > 0) {
			List<Form20OPPModel> listModel = new ArrayList<Form20OPPModel>(
					users.size());
			for (User user : users) {
				if (user.getActs().size() == 1) {
					Form20OPPModel form20OPPModel = new Form20OPPModel(user);
					listModel.add(form20OPPModel);
				}
			}
			return listModel;
		}
		return null;
	}

}
