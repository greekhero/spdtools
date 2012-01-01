package ua.org.tumakha.spd.template.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ua.org.tumakha.spd.entity.Address;
import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.template.Template;

/**
 * @author Yuriy Tumakha
 */
public class TaxSystemStatementModel extends TemplateModel {

	private static final DateFormat regDateFormat = new SimpleDateFormat(
			"dd.MM.yyyy");
	private static final DateFormat regDateDPIFormat = new SimpleDateFormat(
			"ddMMyyyy");
	private static final DateFormat startDateFormat = new SimpleDateFormat(
			"«dd»  MMMMM  yyyy", uaLocale);

	private String firstname;
	private String middlename;
	private String lastname;
	private String firstnameEn;
	private String lastnameEn;
	private String PIN;
	private String regDPI;
	private String regNumberDPI;
	private String regDateDPI;
	private String regDocumentName;
	private String regNumber;
	private String regDate;
	private String startDate;
	private String postalCode;
	private String address;
	private String region;
	private String district;
	private String city;
	private String street;
	private String house;
	private String slashHouse;
	private String apartment;
	private String phone;
	private String email;
	private String kvedCode1;
	private String kvedCode2;
	private String kvedCode3;
	private String kvedCode4;
	private String kvedCode5;
	private String kvedName1;
	private String kvedName2;
	private String kvedName3;
	private String kvedName4;
	private String kvedName5;

	public TaxSystemStatementModel() {
	}

	public TaxSystemStatementModel(User user) {
		copyProperties(user);
	}

	private void copyProperties(User user) {
		firstname = user.getFirstname();
		firstnameEn = user.getFirstnameEn();
		middlename = user.getMiddlename();
		lastname = user.getLastname();
		lastnameEn = user.getLastnameEn();
		PIN = user.getPIN().toString();
		if (user.getRegDPI() != null) {
			regDPI = user.getRegDPI();
		}
		regNumberDPI = user.getRegNumberDPI() != null ? user.getRegNumberDPI()
				.toString() : "";
		regDateDPI = user.getRegDateDPI() != null ? regDateDPIFormat
				.format(user.getRegDateDPI()) : "";
		regDocumentName = 1 != 1 ? "Виписка з єдиного державного реєстру юридичних осіб та фізичних осіб-підприємців"
				: "Свідоцтво про державну реєстрацію";
		regNumber = user.getRegNumber();
		regDate = regDateFormat.format(user.getRegDate());
		GregorianCalendar cal = new GregorianCalendar();
		cal.clear();
		cal.set(2012, Calendar.JANUARY, 1);
		startDate = startDateFormat.format(cal.getTime());
		Address adr = user.getAddress();
		if (adr.getPostalCode() != null) {
			postalCode = String.format("%05d", adr.getPostalCode());
		}
		address = adr.getTextReversedUa();
		region = adr.getRegion() != null ? adr.getRegion() : "";
		district = adr.getDistrict() != null ? adr.getDistrict() : "";
		city = adr.getCity();
		street = adr.getStreet() != null ? adr.getStreet() : "";
		if (adr.getHouse() != null) {
			house = "" + adr.getHouse();
			if (adr.getHouseChar() != null) {
				house += "-" + adr.getHouseChar();
			}
		}
		slashHouse = adr.getSlashHouse() != null ? "" + adr.getSlashHouse()
				: "";
		if (adr.getApartment() != null) {
			apartment = "" + adr.getApartment();
			if (adr.getApartmentChar() != null) {
				apartment += "-" + adr.getApartmentChar();
			}
		}
		phone = user.getPhone() != null ? user.getPhone().toString() : "";
		email = user.getEmail() != null ? user.getEmail() : "";
	}

	@Override
	public String getOutputFilename(Template template) {
		return String.format("/Tax_System_Statement/%s_%s_%s", lastnameEn,
				firstnameEn, template.getFilename());
	}

	private String pinAt(int index) {
		return "" + PIN.charAt(index);
	}

	private String postalCodeAt(int index) {
		if (postalCode != null && postalCode.length() > index) {
			return "" + postalCode.charAt(index);
		} else {
			return "";
		}
	}

	private String regNumberDPIAt(int index) {
		if (regNumberDPI != null && regNumberDPI.length() > index) {
			return "" + regNumberDPI.charAt(index);
		} else {
			return "";
		}
	}

	private String regDateDPIAt(int index) {
		if (regDateDPI != null && regDateDPI.length() > index) {
			return "" + regDateDPI.charAt(index);
		} else {
			return "";
		}
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstnameEn() {
		return firstnameEn;
	}

	public void setFirstnameEn(String firstnameEn) {
		this.firstnameEn = firstnameEn;
	}

	public String getLastnameEn() {
		return lastnameEn;
	}

	public void setLastnameEn(String lastnameEn) {
		this.lastnameEn = lastnameEn;
	}

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
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

	public String getRegDocumentName() {
		return regDocumentName;
	}

	public void setRegDocumentName(String regDocumentName) {
		this.regDocumentName = regDocumentName;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getKvedCode1() {
		return kvedCode1;
	}

	public void setKvedCode1(String kvedCode1) {
		this.kvedCode1 = kvedCode1;
	}

	public String getKvedCode2() {
		return kvedCode2;
	}

	public void setKvedCode2(String kvedCode2) {
		this.kvedCode2 = kvedCode2;
	}

	public String getKvedCode3() {
		return kvedCode3;
	}

	public void setKvedCode3(String kvedCode3) {
		this.kvedCode3 = kvedCode3;
	}

	public String getKvedCode4() {
		return kvedCode4;
	}

	public void setKvedCode4(String kvedCode4) {
		this.kvedCode4 = kvedCode4;
	}

	public String getKvedCode5() {
		return kvedCode5;
	}

	public void setKvedCode5(String kvedCode5) {
		this.kvedCode5 = kvedCode5;
	}

	public String getKvedName1() {
		return kvedName1;
	}

	public void setKvedName1(String kvedName1) {
		this.kvedName1 = kvedName1;
	}

	public String getKvedName2() {
		return kvedName2;
	}

	public void setKvedName2(String kvedName2) {
		this.kvedName2 = kvedName2;
	}

	public String getKvedName3() {
		return kvedName3;
	}

	public void setKvedName3(String kvedName3) {
		this.kvedName3 = kvedName3;
	}

	public String getKvedName4() {
		return kvedName4;
	}

	public void setKvedName4(String kvedName4) {
		this.kvedName4 = kvedName4;
	}

	public String getKvedName5() {
		return kvedName5;
	}

	public void setKvedName5(String kvedName5) {
		this.kvedName5 = kvedName5;
	}

}
