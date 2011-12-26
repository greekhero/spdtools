package ua.org.tumakha.spd.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.services.UserService;

/**
 * @author Yuriy Tumakha
 */
public class UserServiceImplTest {

    private final Log log = LogFactory.getLog(getClass());
    private static String[] CONFIG_LOCATIONS = { "classpath:datasource-test.xml", "classpath:persistenceContext.xml" };
    private static ApplicationContext applicationContext;
    private static UserService userService;

    @BeforeClass
    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext(CONFIG_LOCATIONS);
        userService = (UserService) applicationContext.getBean("userService");

    }

    @Test
    public void testCreateUser() {
        userService.createUser(null);
        userService.createUser(new User());
    }

}
