package ua.org.tumakha.spdtool.template;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.enums.RentType;
import ua.org.tumakha.spdtool.services.TemplateService;
import ua.org.tumakha.spdtool.services.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yuriy Tumakha
 */
public class XlsProcessorTest {

	private static String[] CONFIG_LOCATIONS = { "classpath:datasource-test.xml",
			"classpath:META-INF/spring/applicationContext.xml" };
	private static ApplicationContext applicationContext;
	private static UserService userService;
	private static TemplateService templateService;
	private final XlsProcessor xlsProcessor = new XlsProcessor();

	private static final DateFormat UA_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	@BeforeClass
	public static void init() {
		applicationContext = new ClassPathXmlApplicationContext(CONFIG_LOCATIONS);
		userService = (UserService) applicationContext.getBean("userService");
		templateService = (TemplateService) applicationContext.getBean("templateService");
        XlsProcessor.setReportsDirectory("target");
	}

	@Test
	public void testXlsEsvD5() throws Exception {

		int year = 2012;

		int i = 0;
		List<User> users = userService.findUsersByGroup(1);
		if (users != null) {
			for (User user : users) {
				if (user.isActive()) {
					Map<String, Object> beans = new HashMap<String, Object>();
					beans.put("user", user);
					String outputFilename = String.format("/ESV_d5/%s_%s_%d_", user.getLastnameEn(),
							user.getFirstnameEn(), year);
					xlsProcessor.saveReport(XlsTemplate.ESV_D5, outputFilename, beans);
					i++;
				}
			}
		}
		System.out.println("Generated ESV_d5 reports: " + i);
	}

	@Test
	public void testXlsPayments() throws Exception {

		int year = 2013;
		int month = 1;
		boolean showTaxPayment = false;

		int i = 0;
		List<User> users = userService.findUsersByGroup(1);
		if (users != null) {
			for (User user : users) {
				if (user.isActive()) {
					boolean showRentPayment = false;
					Map<String, Object> beans = new HashMap<String, Object>();
					if (user.getRentType() != null && !user.getRentType().equals(RentType.NONE)) {
						showRentPayment = true;
						boolean rentEquipment = user.getRentType().equals(RentType.OFFICE_EQUIPMENT);
						String strRentEquipment = rentEquipment ? " та обладнання" : "";
						int rentAmount = rentEquipment ? 1350 : 642;
						beans.put("strRentEquipment", strRentEquipment);
						beans.put("rentAmount", rentAmount);
						beans.put("rentContractDate", UA_DATE_FORMAT.format(user.getRentContractDate()));
					}
					beans.put("user", user);
					beans.put("showTaxPayment", showTaxPayment);
					beans.put("showRentPayment", showRentPayment);
					String outputFilenamePrefix = String.format("/Payments/%d_%02d/%s_%s_%d_%02d_", year, month,
							user.getLastnameEn(), user.getFirstnameEn(), year, month);
					xlsProcessor.saveReport(XlsTemplate.PAYMENTS, outputFilenamePrefix, beans);
					i++;
				}
			}
		}
		System.out.println("Generated user Payment files: " + i);
	}
}
