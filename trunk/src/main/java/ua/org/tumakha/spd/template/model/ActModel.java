package ua.org.tumakha.spd.template.model;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import ua.org.tumakha.spd.entity.Act;
import ua.org.tumakha.spd.entity.Address;
import ua.org.tumakha.spd.entity.Bank;
import ua.org.tumakha.spd.entity.Contract;
import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.template.DocxTemplate.Template;
import ua.org.tumakha.util.NumberUtil;

/**
 * @author Yuriy Tumakha
 */
public class ActModel extends TemplateModel {

    private static final Locale uaLocale = new Locale("uk", "UA");
    private static final SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat regDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final SimpleDateFormat contractDateFormat = new SimpleDateFormat("dd MMMMM yyyy", uaLocale);
    private static final SimpleDateFormat contractDateFormatEn = new SimpleDateFormat("dd MMMMM yyyy");
    private static final SimpleDateFormat actDateFormat = new SimpleDateFormat("«dd»   MMMMM   yyyy р", uaLocale);
    private static final SimpleDateFormat actDateFormatEn = new SimpleDateFormat("«dd»   MMMMM  yyyy");
    private static final SimpleDateFormat actPeriodFormat = new SimpleDateFormat("dd MMMMM yyyy", uaLocale);
    private static final SimpleDateFormat actPeriodFormatEn = new SimpleDateFormat("dd of MMMMM yyyy");

    private String actNo;
    private String contractNo;
    private String contractDate;
    private String contractDateEn;
    private String date;
    private String dateEn;
    private String dateFrom;
    private String dateFromEn;
    private String dateTo;
    private String dateToEn;
    private String firstname;
    private String firstnameEn;
    private String middlename;
    private String middlenameEn;
    private String lastname;
    private String lastnameEn;
    private String amountDigit;
    private String amountUa;
    private String amountEn;
    private String regNumber;
    private String regDate;
    private String regAddress;
    private String regAddressEn;
    private String PIN;
    private String bankAccount;
    private String bankName;
    private String bankNameEn;
    private String bankMFO;
    private String bankSWIFT;

    public ActModel() {
    }

    public ActModel(User user) {
        Act lastAct = user.getLastAct();
        Contract lastContract = user.getLastContract();
        Bank bank = user.getBank();
        Address address = user.getAddress();
        setTemplateDate(lastAct.getDateTo());
        actNo = lastAct.getNumber();
        contractNo = lastContract.getNumber();
        contractDate = contractDateFormat.format(lastContract.getDate());
        contractDateEn = contractDateFormatEn.format(lastContract.getDate());
        date = actDateFormat.format(lastAct.getDateTo());
        dateEn = actDateFormatEn.format(lastAct.getDateTo());
        dateFrom = actPeriodFormat.format(lastAct.getDateFrom());
        dateFromEn = actPeriodFormatEn.format(lastAct.getDateFrom());
        dateTo = actPeriodFormat.format(lastAct.getDateTo());
        dateToEn = actPeriodFormatEn.format(lastAct.getDateTo());
        firstname = user.getFirstname();
        firstnameEn = user.getFirstnameEn();
        middlename = user.getMiddlename();
        middlenameEn = user.getMiddlenameEn();
        lastname = user.getLastname();
        lastnameEn = user.getLastnameEn();
        int amount = lastAct.getAmount().intValue();
        amountDigit = "" + amount;
        amountUa = NumberUtil.numberInWordsUa(amount) + " " + NumberUtil.numberInDollarsUa(amount);
        amountEn = NumberUtil.numberInWordsEn(amount);
        regNumber = user.getRegNumber();
        regDate = regDateFormat.format(user.getRegDate());
        regAddress = address.getTextUa() + ", Україна";
        regAddressEn = address.getTextEn() + ", Ukraine";
        PIN = user.getPIN().toString();
        bankAccount = bank.getAccountNumber().toString();
        bankName = bank.getName();
        bankNameEn = bank.getNameEn();
        bankMFO = bank.getMFO().toString();
        bankSWIFT = bank.getSWIFT();
    }

    public String getActNo() {
        return actNo;
    }

    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getContractDateEn() {
        return contractDateEn;
    }

    public void setContractDateEn(String contractDateEn) {
        this.contractDateEn = contractDateEn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateEn() {
        return dateEn;
    }

    public void setDateEn(String dateEn) {
        this.dateEn = dateEn;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateFromEn() {
        return dateFromEn;
    }

    public void setDateFromEn(String dateFromEn) {
        this.dateFromEn = dateFromEn;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateToEn() {
        return dateToEn;
    }

    public void setDateToEn(String dateToEn) {
        this.dateToEn = dateToEn;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstnameEn() {
        return firstnameEn;
    }

    public void setFirstnameEn(String firstnameEn) {
        this.firstnameEn = firstnameEn;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getMiddlenameEn() {
        return middlenameEn;
    }

    public void setMiddlenameEn(String middlenameEn) {
        this.middlenameEn = middlenameEn;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastnameEn() {
        return lastnameEn;
    }

    public void setLastnameEn(String lastnameEn) {
        this.lastnameEn = lastnameEn;
    }

    public String getAmountDigit() {
        return amountDigit;
    }

    public void setAmountDigit(String amountDigit) {
        this.amountDigit = amountDigit;
    }

    public String getAmountUa() {
        return amountUa;
    }

    public void setAmountUa(String amountUa) {
        this.amountUa = amountUa;
    }

    public String getAmountEn() {
        return amountEn;
    }

    public void setAmountEn(String amountEn) {
        this.amountEn = amountEn;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getRegAddressEn() {
        return regAddressEn;
    }

    public void setRegAddressEn(String regAddressEn) {
        this.regAddressEn = regAddressEn;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String pIN) {
        PIN = pIN;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNameEn() {
        return bankNameEn;
    }

    public void setBankNameEn(String bankNameEn) {
        this.bankNameEn = bankNameEn;
    }

    public String getBankMFO() {
        return bankMFO;
    }

    public void setBankMFO(String bankMFO) {
        this.bankMFO = bankMFO;
    }

    public String getBankSWIFT() {
        return bankSWIFT;
    }

    public void setBankSWIFT(String bankSWIFT) {
        this.bankSWIFT = bankSWIFT;
    }

    @Override
    public HashMap<String, String> getMappings() {
        HashMap<String, String> mappings = new HashMap<String, String>();
        mappings.put("actNo", getActNo());
        mappings.put("contractNo", getContractNo());
        mappings.put("contractDate", getContractDate());
        mappings.put("contractDateEn", getContractDateEn());
        mappings.put("date", getDate());
        mappings.put("dateEn", getDateEn());
        mappings.put("dateFrom", getDateFrom());
        mappings.put("dateFromEn", getDateFromEn());
        mappings.put("dateTo", getDateTo());
        mappings.put("dateToEn", getDateToEn());
        mappings.put("firstname", getFirstname());
        mappings.put("firstnameEn", getFirstnameEn());
        mappings.put("middlename", getMiddlename());
        mappings.put("middlenameEn", getMiddlenameEn());
        mappings.put("lastname", getLastname());
        mappings.put("lastnameEn", getLastnameEn());
        mappings.put("amountDigit", getAmountDigit());
        mappings.put("amountUa", getAmountUa());
        mappings.put("amountEn", getAmountEn());
        mappings.put("regNumber", getRegNumber());
        mappings.put("regDate", getRegDate());
        mappings.put("regAddress", getRegAddress());
        mappings.put("regAddressEn", getRegAddressEn());
        mappings.put("PIN", getPIN());
        mappings.put("bankAccount", getBankAccount());
        mappings.put("bankName", getBankName());
        mappings.put("bankNameEn", getBankNameEn());
        mappings.put("bankMFO", getBankMFO());
        mappings.put("bankSWIFT", getBankSWIFT());
        return mappings;
    }

    @Override
    public String getOutputFilename(Template template) {
        String month = yearMonthFormat.format(getTemplateDate());
        return String.format("/%s/ICGU_%s_%s_%s", month, firstnameEn.substring(0, 1) + middlenameEn.charAt(0)
                + lastnameEn.charAt(0), month.replace("-", "_"), template.getFilename());

    }

}
