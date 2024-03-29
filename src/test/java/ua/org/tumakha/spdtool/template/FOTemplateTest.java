package ua.org.tumakha.spdtool.template;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.org.tumakha.spdtool.template.model.ActModel;
import ua.org.tumakha.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class FOTemplateTest {

    private static FOProcessor foProcessor;
    private static String[] CONFIG_LOCATIONS = {
            "classpath:datasource-test.xml",
            "classpath:META-INF/spring/applicationContext.xml" };
    private static ApplicationContext applicationContext;

    @BeforeClass
    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext(
                CONFIG_LOCATIONS);
        foProcessor = new FOProcessor(Executors.newFixedThreadPool(4));
        FOProcessor.setReportsDirectory("target");
    }

	@Test
	public void test() throws Exception {
		ActModel actModel = new ActModel();
		actModel.setActNo("YVT/2011-05-08");
		actModel.setContractNo("YVT/2011-05");
		actModel.setContractDate("18 травня 2011");
		actModel.setContractDateEn("18 May 2011");
		actModel.setStartContractDate("«24»  вересня  2011 р");
		actModel.setStartContractDateEn("«24»  September  2011");
		actModel.setDate("31 грудня 2011 р.");
		actModel.setDateEn("31 of December 2011");
		actModel.setDateFrom("01 грудня 2011");
		actModel.setDateFromEn("01 of December 2011");
		actModel.setDateTo("31 грудня 2011");
		actModel.setDateToEn("31 of December 2011");
		actModel.setFirstname("Юрій");
		actModel.setFirstnameEn("Yuriy");
		actModel.setMiddlename("Володимирович");
		actModel.setMiddlenameEn("Volodymyrovych");
		actModel.setLastname("Тумаха");
		actModel.setLastnameEn("Tumakha");
		actModel.setAmountDigit("4,762");
		actModel.setAmountUa(NumberUtil.numberInWordsUa(4762) + " долари");
		actModel.setAmountEn(NumberUtil.numberInWordsEn(4762));
		actModel.setRegNumber("123456 4455667 45666");
		actModel.setRegNumberEn("123456 4455667 45666");
		actModel.setRegDate("18.12.2010");
		actModel.setRegAddress("вул. Горького, б.32,  м. Київ,Україна");
		actModel.setRegAddressEn("str. Horkogo, 32,  Kyiv, Ukraine");
		actModel.setPIN("1234512345");
		actModel.setBankAccount("2600123456789");
		actModel.setBankName("АТ «Аваль»");
		actModel.setBankNameEn("Aval, Branch, Kyiv, Ukraine");
		actModel.setBankMFO("300235");
		actModel.setBankSWIFT("AVALAUK");
		List<ActModel> listModel = new ArrayList<ActModel>(1);
		listModel.add(actModel);
		foProcessor.saveReports(FOTemplate.CONTRACT, listModel, FOType.PDF);
        foProcessor.shutdown();
	}

}
