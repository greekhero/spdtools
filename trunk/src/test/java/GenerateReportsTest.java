import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.org.tumakha.spd.services.TemplateService;
import ua.org.tumakha.spd.services.UserService;
import ua.org.tumakha.spd.template.DocxTemplate;
import ua.org.tumakha.spd.template.Template;
import ua.org.tumakha.spd.template.model.ActModel;
import ua.org.tumakha.spd.template.model.Form20OPPModel;
import ua.org.tumakha.spd.template.model.IncomeCalculationModel;
import ua.org.tumakha.spd.template.model.TaxSystemStatementModel;

/**
 * @author Yuriy Tumakha
 */
public class GenerateReportsTest {

	private static String[] CONFIG_LOCATIONS = {
			"classpath:datasource-test.xml", "classpath:persistenceContext.xml" };
	private static ApplicationContext applicationContext;
	private static UserService userService;
	private static TemplateService templateService;
	private final DocxTemplate docxTemplate = new DocxTemplate();

	@BeforeClass
	public static void init() {
		applicationContext = new ClassPathXmlApplicationContext(
				CONFIG_LOCATIONS);
		userService = (UserService) applicationContext.getBean("userService");
		templateService = (TemplateService) applicationContext
				.getBean("templateService");

	}

	@Test
	public void testGenerateReports() throws Exception {
		List<ActModel> listModel = templateService.getActModelList();
		docxTemplate.saveReports(Template.ACT, listModel);
		docxTemplate.saveReports(Template.CONTRACT_AMENDMENT, listModel);
		if (listModel != null) {
			System.out.println("Generated report models: " + listModel.size());
		}
	}

	@Test
	public void testGenerateForm20OPP() throws Exception {
		List<Form20OPPModel> listModel = templateService
				.getForm20OPPModelList();
		docxTemplate.saveReports(Template.FORM_20_OPP, listModel);
		if (listModel != null) {
			System.out.println("Generated 20-OPP forms: " + listModel.size());
		}
	}

	@Test
	public void testGenerateTaxSystemStatement() throws Exception {
		List<TaxSystemStatementModel> listModel = templateService
				.getTaxSystemStatementModelList(1);
		docxTemplate.saveReports(Template.TAX_SYSTEM_STATEMENT, listModel);
		List<IncomeCalculationModel> models = templateService
				.getIncomeCalculationModelList(1);
		docxTemplate.saveReports(Template.INCOME_CALCULATION, models);
		if (listModel != null) {
			System.out.println("Generated TaxSystem statements: "
					+ listModel.size());
			System.out.println("Generated Income calculations: "
					+ models.size());
		}
	}

}
