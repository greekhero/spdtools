package ua.org.tumakha.spdtool.template;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.org.tumakha.spdtool.template.model.ActModel;
import ua.org.tumakha.util.NumberUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class DocxTemplateTest {

	private static DocxProcessor docxProcessor;
    private static String[] CONFIG_LOCATIONS = {
            "classpath:datasource-test.xml",
            "classpath:META-INF/spring/applicationContext.xml" };
    private static ApplicationContext applicationContext;

    private static final Locale UA_LOCALE = new Locale("uk", "UA");
    private static final Locale EN_LOCALE = Locale.ENGLISH;
    private static final DateFormat ACT_DATE_FORMAT = new SimpleDateFormat("«dd»  MMMMM  yyyy р", UA_LOCALE);
    private static final DateFormat ACT_DATE_FORMAT_EN = new SimpleDateFormat("«dd»  MMMMM  yyyy", EN_LOCALE);

    @BeforeClass
    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext(
                CONFIG_LOCATIONS);
        docxProcessor = new DocxProcessor(Executors.newFixedThreadPool(4));
        DocxProcessor.setReportsDirectory("target");
    }

    @Test
	public void test() throws Exception {
		ActModel actModel = new ActModel();
        actModel.setStartContractDate(ACT_DATE_FORMAT.format(new Date()));
        actModel.setStartContractDateEn(ACT_DATE_FORMAT_EN.format(new Date()));
		actModel.setActNo("YVT/2011-05-08");
		actModel.setContractNo("YVT/2011-05");
		actModel.setContractDate("18 травня 2011");
		actModel.setContractDateEn("18 May 2011");
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
		actModel.setRegDate("18.12.2010");
		actModel.setRegAddress("вул. Горького, б.32,  м. Київ,Україна");
		actModel.setRegAddressEn("str. Horkogo, 32,  Kyiv, Ukraine");
		actModel.setPIN("1234512345");
		actModel.setBankAccount("2600123456789");
		actModel.setBankName("АТ «Аваль»");
		actModel.setBankNameEn("Aval, Branch, Kyiv, Ukraine");
		actModel.setBankMFO("300235");
		actModel.setBankSWIFT("AVALAUK");
        actModel.setCorrespondentBank("Bank Trust Company Americas, NY\n" +
                "SWIFT CODE: ZZZZZ\n" +
                "ROUTING NUMBER: 111111\n" +
                "Correspondent account of OTHER BANK");
		List<ActModel> listModel = new ArrayList<ActModel>(100);
		listModel.add(actModel);
        docxProcessor.saveReports(DocxTemplate.ACT, listModel);
        docxProcessor.saveReports(DocxTemplate.CONTRACT, listModel);
        docxProcessor.saveReports(DocxTemplate.CONTRACT_ANNEX, listModel);
        docxProcessor.shutdown();
	}

}
