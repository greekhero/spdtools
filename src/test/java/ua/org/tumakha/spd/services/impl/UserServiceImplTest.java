package ua.org.tumakha.spd.services.impl;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.org.tumakha.spd.entity.Address;
import ua.org.tumakha.spd.entity.Bank;
import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.services.UserService;

/**
 * @author Yuriy Tumakha
 */
public class UserServiceImplTest {

	private static String[] CONFIG_LOCATIONS = {
			"classpath:datasource-test.xml", "classpath:persistenceContext.xml" };
	private static ApplicationContext applicationContext;
	private static UserService userService;

	@BeforeClass
	public static void init() {
		applicationContext = new ClassPathXmlApplicationContext(
				CONFIG_LOCATIONS);
		userService = (UserService) applicationContext.getBean("userService");

	}

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setActive(true);
		user.setPIN(1122334455L);
		user.setFirstname("Юрій");
		user.setFirstnameEn("Yuriy");
		user.setMiddlename("Володимирович");
		user.setMiddlenameEn("Volodymyrovych");
		user.setLastname("Тумаха");
		user.setLastnameEn("Tumakha");
		user.setRegNumber("123456 4455667 45666");
		user.setRegDate(new Date());

		Address address = new Address();
		address.setUser(user);
		address.setCity("Київ");
		address.setCityEn("Kyiv");
		address.setStreet("Горького");
		address.setStreetEn("Gorkogo");
		address.setHouse(13);
		address.setHouseChar("Б");
		address.setHouseCharEn("B");
		address.setApartment(100);
		user.setAddress(address);

		Bank bank = new Bank();
		bank.setUser(user);
		bank.setAccountNumber(2600123456789L);
		bank.setName("АТ «Аваль»");
		bank.setNameEn("Aval, Branch, Kyiv, Ukraine");
		bank.setMFO(300235);
		bank.setSWIFT("AVALAUK");
		user.setBank(bank);

		// userService.createUser(user);

		System.out.println("userId = " + user.getUserId());

	}

}
