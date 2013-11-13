package ua.org.tumakha;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.BankTransactionService;
import ua.org.tumakha.spdtool.services.TemplateService;
import ua.org.tumakha.spdtool.services.UserService;
import ua.org.tumakha.spdtool.template.XlsProcessor;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yuriy Tumakha
 */
public class BankDataExport {

    private static final Log log = LogFactory.getLog(BankDataExport.class);
    private static String[] CONFIG_LOCATIONS = { "classpath:datasource-test.xml",
            "classpath:META-INF/spring/applicationContext.xml" };
    private static ApplicationContext applicationContext;
    private static BankTransactionService bankTransactionService;
    private static UserService userService;
    private static TemplateService templateService;
    private final XlsProcessor xlsProcessor = new XlsProcessor();

    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext(CONFIG_LOCATIONS);
        bankTransactionService = (BankTransactionService) applicationContext.getBean("bankTransactionService");
        userService = (UserService) applicationContext.getBean("userService");
        templateService = (TemplateService) applicationContext.getBean("templateService");
    }

    public static void main(final String[] args) throws IOException, InvalidFormatException, ParseException {
        init();

        int year = 2013;

        List<User> users = userService.findActiveUsers();
        Set<Integer> enabledUserIds = new HashSet<Integer>();
        for (User user : users) {
            enabledUserIds.add(user.getUserId());
        }

        List<String> fileNames = templateService.generateBankExtract(enabledUserIds, year);
        log.debug("Generated files: " + fileNames.size());
    }

}
