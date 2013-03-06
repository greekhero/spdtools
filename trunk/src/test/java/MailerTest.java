import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * @author Yuriy Tumakha
 */
public class MailerTest {

	private static String[] CONFIG_LOCATIONS = { "classpath:datasource-test.xml",
			"classpath:META-INF/spring/applicationContext.xml" };
	private static ApplicationContext applicationContext;
	private static JavaMailSender mailSender;

	@BeforeClass
	public static void init() {
		applicationContext = new ClassPathXmlApplicationContext(CONFIG_LOCATIONS);
		mailSender = (JavaMailSender) applicationContext.getBean("mailSender");
	}

	@Test
	public void testSendEmail() throws Exception {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("tumakha@gmail.com"));
				mimeMessage.setSubject("Test SMTP");
				mimeMessage.setText("Hello,\n\nTest SMTP.");
			}
		};
		mailSender.send(preparator);
	}
}
