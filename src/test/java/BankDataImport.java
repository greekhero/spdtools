import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.org.tumakha.spdtool.entity.BankTransaction;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.BankTransactionService;
import ua.org.tumakha.spdtool.services.UserService;
import ua.org.tumakha.spdtool.xml.BankData;

public class BankDataImport {

	private static final String CHARSET = "windows-1251";// Cp1251
	private static final String[] EXTENSIONS = { "xml" };

	private static final Log log = LogFactory.getLog(BankDataImport.class);
	private static String[] CONFIG_LOCATIONS = { "classpath:datasource-test.xml",
			"classpath:META-INF/spring/applicationContext.xml" };
	private static ApplicationContext applicationContext;
	private static BankTransactionService bankTransactionService;
	private static UserService userService;

	public static void init() {
		applicationContext = new ClassPathXmlApplicationContext(CONFIG_LOCATIONS);
		bankTransactionService = (BankTransactionService) applicationContext.getBean("bankTransactionService");
		userService = (UserService) applicationContext.getBean("userService");
	}

	public static void main(final String[] args) throws JAXBException, IOException {
		init();

		JAXBContext context = JAXBContext.newInstance(BankData.class);
		Unmarshaller um = context.createUnmarshaller();
		File directory = new File(args[0]);
		for (Object file : FileUtils.listFiles(directory, EXTENSIONS, false)) {

			System.out.println(file);

			log.debug(file);
			InputStreamReader reader = new InputStreamReader(new FileInputStream((File) file), CHARSET);
			BankData bankData = (BankData) um.unmarshal(reader);
			User user = null;
			for (BankTransaction transaction : bankData.getBankTransactions()) {
				if (user == null) {
					Long pin = transaction.getIdentifyCode();
					try {
						user = userService.findUserByPIN(pin);
					} catch (Exception e) {
						log.error("User not found with PIN " + pin, e);
						throw new IllegalArgumentException("User not found with PIN " + pin);
					}

					System.out.println(transaction.getId() + "  " + transaction.getOperationId() + "  "
							+ transaction.getCurrSymbolCode() + "  " + transaction.getDocumentTypeId() + "."
							+ transaction.getDocSubTypesName() + "\t\t" + transaction.getCorrContragentsName() + "\t"
							+ transaction.getPlatPurpose());
				}
				transaction.setUser(user);
				BankTransaction existTransaction = bankTransactionService.findTransactionByUserAndId(user.getUserId(),
						transaction.getId());
				if (existTransaction == null) {
					bankTransactionService.createTransaction(transaction);
				} else if (!existTransaction.getCurrSymbolCode().equals(transaction.getCurrSymbolCode())
                        || !existTransaction.getSumma().equals(transaction.getSumma())) {
                    log.error(String.format("Incorrect transaction(id=%s) exists in DB.", transaction.getId()));
                }
			}
		}
	}
}