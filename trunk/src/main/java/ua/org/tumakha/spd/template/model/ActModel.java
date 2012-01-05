package ua.org.tumakha.spd.template.model;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ua.org.tumakha.spd.entity.Act;
import ua.org.tumakha.spd.entity.Address;
import ua.org.tumakha.spd.entity.Bank;
import ua.org.tumakha.spd.entity.Contract;
import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.template.Template;
import ua.org.tumakha.util.NumberUtil;

/**
 * @author Yuriy Tumakha
 */
public class ActModel extends TemplateModel {

	private static final DateFormat yearMonthFormat = new SimpleDateFormat(
			"yyyy-MM");
	private static final DateFormat regDateFormat = new SimpleDateFormat(
			"dd.MM.yyyy");
	private static final DateFormat contractDateFormat = new SimpleDateFormat(
			"dd MMMMM yyyy", uaLocale);
	private static final DateFormat contractDateFormatEn = new SimpleDateFormat(
			"dd MMMMM yyyy", enLocale);
	private static final DateFormat actDateFormat = new SimpleDateFormat(
			"«dd»  MMMMM  yyyy р", uaLocale);
	private static final DateFormat actDateFormatEn = new SimpleDateFormat(
			"«dd»  MMMMM  yyyy", enLocale);
	private static final DateFormat actPeriodFormat = new SimpleDateFormat(
			"dd MMMMM yyyy", uaLocale);
	private static final DateFormat actPeriodFormatEn = new SimpleDateFormat(
			"dd 'of' MMMMM yyyy", enLocale);
	private static final NumberFormat amountFormat = NumberFormat
			.getNumberInstance(enLocale);

	private String actNo;
	private String contractNo;
	private String contractDate;
	private String contractDateEn;
	private String serviceType;
	private String serviceTypeVOrudnomu;
	private String serviceTypeEn;
	private String date;
	private String dateEn;
	private String startDate;
	private String startDateEn;
	private String dateFrom;
	private String dateFromEn;
	private String dateFromDigit;
	private String dateTo;
	private String dateToEn;
	private String dateToDigit;
	private String firstname;
	private String firstnameEn;
	private String middlename;
	private String middlenameEn;
	private String lastname;
	private String lastnameEn;
	private String amountDigit;
	private String amountUa;
	private String amountEn;
	private String regDocumentName;
	private String regDocumentNameVOrudnomu;
	private String regDocumentNameEn;
	private String regNumber;
	private String regNumberEn;
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
		copyProperties(user, user.getLastAct());
	}

	public ActModel(Act act) {
		copyProperties(act.getUser(), act);
	}

	private void copyProperties(User user, Act act) {
		Contract contract = act.getContract();
		Bank bank = user.getBank();
		Address address = user.getAddress();
		actNo = act.getNumber();
		contractNo = contract.getNumber();
		contractDate = contractDateFormat.format(contract.getDate());
		contractDateEn = contractDateFormatEn.format(contract.getDate());
		serviceType = user.getServiceType().getName();
		serviceTypeVOrudnomu = user.getServiceType().getNameVOrudnomu();
		serviceTypeEn = user.getServiceType().getNameEn();
		date = actDateFormat.format(act.getDateTo());
		dateEn = actDateFormatEn.format(act.getDateTo());
		startDate = actDateFormat.format(act.getDateFrom());
		startDateEn = actDateFormatEn.format(act.getDateFrom());
		dateFrom = actPeriodFormat.format(act.getDateFrom());
		dateFromEn = actPeriodFormatEn.format(act.getDateFrom());
		dateFromDigit = regDateFormat.format(act.getDateFrom());
		dateTo = actPeriodFormat.format(act.getDateTo());
		dateToEn = actPeriodFormatEn.format(act.getDateTo());
		dateToDigit = regDateFormat.format(act.getDateTo());
		firstname = user.getFirstname();
		firstnameEn = user.getFirstnameEn();
		middlename = user.getMiddlename();
		middlenameEn = user.getMiddlenameEn();
		lastname = user.getLastname();
		lastnameEn = user.getLastnameEn();
		int amount = act.getAmount().intValue();
		amountDigit = amountFormat.format(amount);
		amountUa = NumberUtil.numberInWordsUa(amount) + " "
				+ NumberUtil.numberInDollarsUa(amount);
		amountEn = NumberUtil.numberInWordsEn(amount);
		if (user.getRegDocumentType() != null) {
			regDocumentName = user.getRegDocumentType().getDescription();
			regDocumentNameVOrudnomu = user.getRegDocumentType()
					.getDescriptionVOrudnomu();
			regDocumentNameEn = user.getRegDocumentType().getDescriptionEn();
		}
		regNumber = user.getRegNumber();
		regNumberEn = regNumber != null ? regNumber.replace("з", "z") : "";
		regDate = user.getRegDate() != null ? regDateFormat.format(user
				.getRegDate()) : "";
		regAddress = address.getTextUa() + ", Україна";
		regAddressEn = address.getTextEn() + ", Ukraine";
		PIN = user.getPIN().toString();
		bankAccount = bank.getAccountNumber().toString();
		bankName = bank.getName();
		bankNameEn = bank.getNameEn();
		bankMFO = bank.getMFO() != null ? bank.getMFO().toString() : "";
		bankSWIFT = bank.getSWIFT();
	}

	public String getN() {
		return "" + Integer.parseInt(actNo.substring(actNo.length() - 2));
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

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceTypeVOrudnomu() {
		return serviceTypeVOrudnomu;
	}

	public void setServiceTypeVOrudnomu(String serviceTypeVOrudnomu) {
		this.serviceTypeVOrudnomu = serviceTypeVOrudnomu;
	}

	public String getServiceTypeEn() {
		return serviceTypeEn;
	}

	public void setServiceTypeEn(String serviceTypeEn) {
		this.serviceTypeEn = serviceTypeEn;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDateEn() {
		return startDateEn;
	}

	public void setStartDateEn(String startDateEn) {
		this.startDateEn = startDateEn;
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

	public String getDateFromDigit() {
		return dateFromDigit;
	}

	public void setDateFromDigit(String dateFromDigit) {
		this.dateFromDigit = dateFromDigit;
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

	public String getDateToDigit() {
		return dateToDigit;
	}

	public void setDateToDigit(String dateToDigit) {
		this.dateToDigit = dateToDigit;
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

	public String getRegDocumentName() {
		return regDocumentName;
	}

	public void setRegDocumentName(String regDocumentName) {
		this.regDocumentName = regDocumentName;
	}

	public String getRegDocumentNameVOrudnomu() {
		return regDocumentNameVOrudnomu;
	}

	public void setRegDocumentNameVOrudnomu(String regDocumentNameVOrudnomu) {
		this.regDocumentNameVOrudnomu = regDocumentNameVOrudnomu;
	}

	public String getRegDocumentNameEn() {
		return regDocumentNameEn;
	}

	public void setRegDocumentNameEn(String regDocumentNameEn) {
		this.regDocumentNameEn = regDocumentNameEn;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getRegNumberEn() {
		return regNumberEn;
	}

	public void setRegNumberEn(String regNumberEn) {
		this.regNumberEn = regNumberEn;
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
	public String getOutputFilename(Template template) {
		String month = "";
		try {
			month = yearMonthFormat.format(actPeriodFormatEn.parse(dateToEn));
		} catch (ParseException e) {
			throw new IllegalStateException(e);
		}
		return String.format("/%s/ICGU_%s_%s_%s_%s", month,
				firstnameEn.substring(0, 1) + middlenameEn.charAt(0)
						+ lastnameEn.charAt(0), month.replace("-", "_"),
				lastnameEn, template.getFilename());

	}

}
