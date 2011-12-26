package ua.org.tumakha.spd.template;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ua.org.tumakha.spd.template.model.ActModel;
import ua.org.tumakha.util.NumberUtil;

public class DocxTemplateTest {

    DocxTemplate docxTemplate = new DocxTemplate();

    @Test
    public void test() throws Exception {
        ActModel actModel = new ActModel();
        actModel.setTemplateDate(new Date());
        actModel.setActNo("TVO/2011-05-08");
        actModel.setContractNo("TVO/2011-05");
        actModel.setContractDate("18 травня 2011");
        actModel.setContractDateEn("18 May 2011");
        actModel.setDate("31 грудня 2011 р.");
        actModel.setDateEn("31 of December 2011");
        actModel.setDateFrom("01 грудня 2011");
        actModel.setDateFromEn("01 of December 2011");
        actModel.setDateTo("31 грудня 2011");
        actModel.setDateToEn("31 of December 2011");
        actModel.setFirstname("Тетяна");
        actModel.setFirstnameEn("Tatiana");
        actModel.setMiddlename("Вікторівна");
        actModel.setMiddlenameEn("Viktorivna");
        actModel.setLastname("Остапенко");
        actModel.setLastnameEn("Ostapenko");
        actModel.setAmountDigit("4,762");
        actModel.setAmountUa(NumberUtil.numberInWordsUa(4762) + " долари");
        actModel.setAmountEn(NumberUtil.numberInWordsEn(4762));
        actModel.setRegNumber("2 500 000 0000 005094");
        actModel.setRegDate("13.05.2011");
        actModel.setRegAddress("вул. Орджонікідзе, б.38/2,  м. Каховка, Херсонська обл., Україна");
        actModel.setRegAddressEn("str. Ordzhonikidze, 38/2,  Kahovka, Kherson region, Ukraine");
        actModel.setPIN("3150900721");
        actModel.setBankAccount("26001101350036");
        actModel.setBankName("АТ «ОТП Банк»");
        actModel.setBankNameEn("OTP BANK,  Branch, Kyiv, Ukraine");
        actModel.setBankMFO("300528");
        actModel.setBankSWIFT("OTPVUAUK");
        List<ActModel> listModel = new ArrayList<ActModel>(100);
        listModel.add(actModel);
        docxTemplate.saveReports(listModel);
    }

}
