package ua.org.tumakha.spdtool.template;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.org.tumakha.spdtool.entity.Declaration;
import ua.org.tumakha.spdtool.entity.Kved;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.TemplateService;
import ua.org.tumakha.spdtool.services.UserService;

/**
 * @author Yuriy Tumakha
 */
public class XlsProcessorTest {

	private static String[] CONFIG_LOCATIONS = {
			"classpath:datasource-test.xml",
			"classpath:META-INF/spring/applicationContext.xml" };
	private static ApplicationContext applicationContext;
	private static UserService userService;
	private static TemplateService templateService;
	private final XlsProcessor xlsProcessor = new XlsProcessor();

	@BeforeClass
	public static void init() {
		applicationContext = new ClassPathXmlApplicationContext(
				CONFIG_LOCATIONS);
		userService = (UserService) applicationContext.getBean("userService");
		templateService = (TemplateService) applicationContext
				.getBean("templateService");

	}

	@Test
	public void testXlsEsvD5() throws Exception {
		List<User> users = userService.findUsersByGroup(1);
		if (users != null) {
			for (User user : users) {
				if (user.isActive()) {
					Map<String, Object> beans = new HashMap<String, Object>();
					beans.put("user", user);
					String outputFilename = String.format("/ESV_d5/%s_%s_",
							user.getLastnameEn(), user.getFirstnameEn());
					xlsProcessor.saveReport(XlsTemplate.ESV_D5, outputFilename,
							beans);
				}
			}
		}
		System.out.println("Generated ESV_d5 reports: " + users.size());
	}

	@Test
	public void testXlsDeclaration() throws Exception {
		List<User> users = templateService.getUsersForDeclaration(1);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		if (month == 0) {
			year = year - 1;
		}
		char[] dateYear = ("" + (month == 11 ? year + 1 : year)).toCharArray();
		int quarter = month == 0 ? 4 : (month + 1) / 3;
		int i = 0;
		if (users != null) {
			for (User user : users) {
				if (user.isActive()) {
					Declaration declaration = user.getDeclarations().get(0);
					Integer income = declaration.getIncome();
					Integer tax = declaration.getTax();
					Map<String, Object> beans = new HashMap<String, Object>();
					beans.put("user", user);
					beans.put("year", year);
					beans.put("dateYear", dateYear);
					beans.put("income", income);
					beans.put("tax", tax);
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
					String outputFilename = String.format(
							"/DECLARATION/%d_Q%d/%s_%s_%d_Q%d_", year, quarter,
							user.getLastnameEn(), user.getFirstnameEn(), year,
							quarter);
					xlsProcessor.saveReport(XlsTemplate.DECLARATION,
							outputFilename, beans);
					i++;
				}
			}
		}
		System.out.println("Generated Declarations: " + i);
	}
}
