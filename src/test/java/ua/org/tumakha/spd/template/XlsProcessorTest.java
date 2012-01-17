package ua.org.tumakha.spd.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.services.UserService;

/**
 * @author Yuriy Tumakha
 */
public class XlsProcessorTest {

	private static String[] CONFIG_LOCATIONS = {
			"classpath:datasource-test.xml", "classpath:persistenceContext.xml" };
	private static ApplicationContext applicationContext;
	private static UserService userService;
	// private static TemplateService templateService;
	private final XlsProcessor xlsProcessor = new XlsProcessor();

	@BeforeClass
	public static void init() {
		applicationContext = new ClassPathXmlApplicationContext(
				CONFIG_LOCATIONS);
		userService = (UserService) applicationContext.getBean("userService");
		// templateService = (TemplateService) applicationContext
		// .getBean("templateService");

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

}
