import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.org.tumakha.spdtool.services.TemplateService;
import ua.org.tumakha.spdtool.services.UserService;
import ua.org.tumakha.spdtool.template.DocxProcessor;
import ua.org.tumakha.spdtool.template.DocxTemplate;
import ua.org.tumakha.spdtool.template.XlsProcessor;
import ua.org.tumakha.spdtool.template.model.*;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author Yuriy Tumakha
 */
public class GenerateReportsTest {

	private static String[] CONFIG_LOCATIONS = {
			"classpath:datasource-test.xml",
			"classpath:META-INF/spring/applicationContext.xml" };
	private static ApplicationContext applicationContext;
	private static UserService userService;
	private static TemplateService templateService;
	private static DocxProcessor docxProcessor;
	private final XlsProcessor xlsProcessor = new XlsProcessor();

	@BeforeClass
	public static void init() {
		applicationContext = new ClassPathXmlApplicationContext(
				CONFIG_LOCATIONS);
		userService = (UserService) applicationContext.getBean("userService");
		templateService = (TemplateService) applicationContext
				.getBean("templateService");
        docxProcessor = new DocxProcessor(Executors.newFixedThreadPool(4));
        DocxProcessor.setReportsDirectory("target");
	}

	@Test
	public void testGenerateReports() throws Exception {
		List<ActModel> listModel = templateService.getActModelList();
		docxProcessor.saveReports(DocxTemplate.CONTRACT, listModel);
		docxProcessor.saveReports(DocxTemplate.CONTRACT_ANNEX, listModel);
		docxProcessor.saveReports(DocxTemplate.ACT, listModel);
//		docxProcessor.saveReports(DocxTemplate.CONTRACT_ADITIONAL_AGREEMENT,
//				listModel);
		if (listModel != null) {
			System.out.println("Generated report models: " + listModel.size());
		}
	}

    @Ignore
	@Test
	public void testGenerateForm20OPP() throws Exception {
		List<Form20OPPModel> listModel = templateService
				.getForm20OPPModelList();
		docxProcessor.saveReports(DocxTemplate.FORM_20_OPP, listModel);
		if (listModel != null) {
			System.out.println("Generated 20-OPP forms: " + listModel.size());
		}
	}

    @Ignore
	@Test
	public void testGenerateForm11Kved() throws Exception {
		List<Form11KvedModel> listModel = templateService
				.getForm11KvedModelList(1);
		docxProcessor.saveReports(DocxTemplate.FORM_11_KVED, listModel);
		if (listModel != null) {
			System.out.println("Generated forms: " + listModel.size());
		}
	}

    @Ignore
	@Test
	public void testGenerateTaxSystemStatement() throws Exception {
		List<TaxSystemStatementModel> listModel = templateService
				.getTaxSystemStatementModelList(1);
		docxProcessor.saveReports(DocxTemplate.TAX_SYSTEM_STATEMENT, listModel);
		List<IncomeCalculationModel> models = templateService
				.getIncomeCalculationModelList(1);
		docxProcessor.saveReports(DocxTemplate.INCOME_CALCULATION, models);
		if (listModel != null) {
			System.out.println("Generated TaxSystem statements: "
					+ listModel.size());
			System.out.println("Generated Income calculations: "
					+ models.size());
		}
	}

    @AfterClass
    public static void shutdown() {
        docxProcessor.shutdown();
    }

}
