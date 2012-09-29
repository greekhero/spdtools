package ua.org.tumakha.spdtool.template.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import ua.org.tumakha.spdtool.entity.Address;
import ua.org.tumakha.spdtool.entity.Bank;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.template.DocxTemplate;
import ua.org.tumakha.spdtool.template.FOTemplate;
import ua.org.tumakha.util.StrUtil;

/**
 * @author Yuriy Tumakha
 */
public abstract class TemplateModel {

	protected static final Locale uaLocale = new Locale("uk", "UA");
	protected static final Locale enLocale = Locale.ENGLISH;
	protected static final DateFormat uaDateFormat = new SimpleDateFormat(
			"dd.MM.yyyy");
	private static final DateFormat regDateDPIFormat = new SimpleDateFormat(
			"ddMMyyyy");

	private String firstname;
	private String firstnameEn;
	private String middlename;
	private String middlenameEn;
	private String middlenameBankEn;
	private String lastname;
	private String lastnameEn;
	private String regDocumentName;
	private String regDocumentNameVOrudnomu;
	private String regDocumentNameEn;
	private String regNumber;
	private String regNumberEn;
	private String regDate;
	private String regDPI;
	private String regNumberDPI;
	private String regDateDPI;
	private String regAddress;
	private String regAddressEn;
	private String postalCode;
	private String region;
	private String district;
	private String city;
	private String street;
	private String house;
	private String slashHouse;
	private String apartment;
	private String PIN;
	private String phone;
	private String email;
	private String bankAccount;
	private String bankName;
	private String bankNameEn;
	private String bankMFO;
	private String bankSWIFT;
	private String correspondentBank;
	private String correspondentBankEn;

	public TemplateModel() {
	}

	public TemplateModel(User user) {
		Bank bank = user.getBank();
		Address address = user.getAddress();
		firstname = user.getFirstname();
		firstnameEn = user.getFirstnameEn();
		middlename = user.getMiddlename();
		middlenameEn = user.getMiddlenameEn();
		middlenameBankEn = bank.isUsedMiddlename() ? user.getMiddlenameEn()
				: "";
		lastname = user.getLastname();
		lastnameEn = user.getLastnameEn();
		if (user.getRegDocumentType() != null) {
			regDocumentName = user.getRegDocumentType().getDescription();
			regDocumentNameVOrudnomu = user.getRegDocumentType()
					.getDescriptionVOrudnomu();
			regDocumentNameEn = user.getRegDocumentType().getDescriptionEn();
		}
		regNumber = user.getRegNumber();
		regNumberEn = regNumber != null ? regNumber.replace("з", "z") : "";
		regDate = user.getRegDate() != null ? uaDateFormat.format(user
				.getRegDate()) : "";
		if (user.getRegDpi() != null) {
			regDPI = user.getRegDpi();
		}
		regNumberDPI = user.getRegNumberDpi() != null ? user.getRegNumberDpi()
				.toString() : "";
		regDateDPI = user.getRegDateDpi() != null ? regDateDPIFormat
				.format(user.getRegDateDpi()) : "";
		regAddress = address.getTextUa() + ", Україна";
		regAddressEn = address.getTextEn() + ", Ukraine";
		if (address.getPostalCode() != null) {
			postalCode = String.format("%05d", address.getPostalCode());
		}
		region = address.getRegion() != null ? address.getRegion() : "";
		district = address.getDistrict() != null ? address.getDistrict() : "";
		city = address.getCity();
		street = address.getStreet() != null ? address.getStreet() : "";
		if (address.getHouse() != null) {
			house = "" + address.getHouse();
			if (address.getHouseChar() != null) {
				house += "-" + address.getHouseChar();
			}
		}
		slashHouse = address.getSlashHouse() != null ? ""
				+ address.getSlashHouse() : "";
		if (address.getApartment() != null) {
			apartment = "" + address.getApartment();
			if (address.getApartmentChar() != null) {
				apartment += "-" + address.getApartmentChar();
			}
		}
		phone = user.getPhone() != null ? user.getPhone().toString() : "";
		email = user.getEmail() != null ? user.getEmail() : "";
		PIN = user.getPin().toString();
		bankAccount = bank.getAccountNumber().toString();
		bankName = bank.getName();
		bankNameEn = bank.getNameEn();
		bankMFO = bank.getMFO() != null ? bank.getMFO().toString() : "";
		bankSWIFT = bank.getSWIFT();
		correspondentBank = bank.getCorrespondentBank();
		correspondentBankEn = bank.getCorrespondentBankEn();
	}

	public abstract String getOutputFilename(DocxTemplate template);

	public String getOutputFilename(FOTemplate template) {
		return "test.pdf";
	}

	private String pinAt(int index) {
		return StrUtil.charAt(PIN, index);
	}

	private String postalCodeAt(int index) {
		return StrUtil.charAt(postalCode, index);
	}

	private String regNumberDPIAt(int index) {
		return StrUtil.charAt(regNumberDPI, index);
	}

	private String regDateDPIAt(int index) {
		return StrUtil.charAt(regDateDPI, index);
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

	public String getMiddlenameBankEn() {
		return middlenameBankEn;
	}

	public void setMiddlenameBankEn(String middlenameBankEn) {
		this.middlenameBankEn = middlenameBankEn;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getCorrespondentBank() {
		return correspondentBank;
	}

	public void setCorrespondentBank(String correspondentBank) {
		this.correspondentBank = correspondentBank;
	}

	public List<String> getCorrespondentBankLines() {
		if (correspondentBank != null) {
			return Arrays.asList(correspondentBank.split("\n"));
		}
		return null;
	}

	public String getCorrespondentBankEn() {
		return correspondentBankEn;
	}

	public void setCorrespondentBankEn(String correspondentBankEn) {
		this.correspondentBankEn = correspondentBankEn;
	}

	public List<String> getCorrespondentBankEnLines() {
		if (correspondentBankEn != null) {
			return Arrays.asList(correspondentBankEn.split("\n"));
		}
		return null;
	}

	public String getPin0() {
		return pinAt(0);
	}

	public String getPin1() {
		return pinAt(1);
	}

	public String getPin2() {
		return pinAt(2);
	}

	public String getPin3() {
		return pinAt(3);
	}

	public String getPin4() {
		return pinAt(4);
	}

	public String getPin5() {
		return pinAt(5);
	}

	public String getPin6() {
		return pinAt(6);
	}

	public String getPin7() {
		return pinAt(7);
	}

	public String getPin8() {
		return pinAt(8);
	}

	public String getPin9() {
		return pinAt(9);
	}

	public String getPost0() {
		return postalCodeAt(0);
	}

	public String getPost1() {
		return postalCodeAt(1);
	}

	public String getPost2() {
		return postalCodeAt(2);
	}

	public String getPost3() {
		return postalCodeAt(3);
	}

	public String getPost4() {
		return postalCodeAt(4);
	}

	public String getNum0() {
		return regNumberDPIAt(0);
	}

	public String getNum1() {
		return regNumberDPIAt(1);
	}

	public String getNum2() {
		return regNumberDPIAt(2);
	}

	public String getNum3() {
		return regNumberDPIAt(3);
	}

	public String getNum4() {
		return regNumberDPIAt(4);
	}

	public String getNum5() {
		return regNumberDPIAt(5);
	}

	public String getDate0() {
		return regDateDPIAt(0);
	}

	public String getDate1() {
		return regDateDPIAt(1);
	}

	public String getDate2() {
		return regDateDPIAt(2);
	}

	public String getDate3() {
		return regDateDPIAt(3);
	}

	public String getDate4() {
		return regDateDPIAt(4);
	}

	public String getDate5() {
		return regDateDPIAt(5);
	}

	public String getDate6() {
		return regDateDPIAt(6);
	}

	public String getDate7() {
		return regDateDPIAt(7);
	}

	public String getRegDPI() {
		return regDPI;
	}

	public void setRegDPI(String regDPI) {
		this.regDPI = regDPI;
	}

	public String getRegNumberDPI() {
		return regNumberDPI;
	}

	public void setRegNumberDPI(String regNumberDPI) {
		this.regNumberDPI = regNumberDPI;
	}

	public String getRegDateDPI() {
		return regDateDPI;
	}

	public void setRegDateDPI(String regDateDPI) {
		this.regDateDPI = regDateDPI;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getSlashHouse() {
		return slashHouse;
	}

	public void setSlashHouse(String slashHouse) {
		this.slashHouse = slashHouse;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

}
