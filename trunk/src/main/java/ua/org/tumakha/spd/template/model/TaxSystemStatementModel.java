package ua.org.tumakha.spd.template.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	private static final DateFormat startDateFormat = new SimpleDateFormat(
			"«dd»  MMMMM  yyyy", uaLocale);

	private String firstname;
	private String middlename;
	private String lastname;
	private String firstnameEn;
	private String lastnameEn;
	private String PIN;
	private String regDPI;
	private String regDocumentName;
	private String regNumber;
	private String regDate;
	private String startDate;
	private String postalCode;
	private String region;
	private String district;
	private String city;
	private String street;
	private String house;
	private String slashHouse;
	private String apartment;
	private String phone;
	private String email;

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
		regDocumentName = 1 != 1 ? "Виписка з єдиного державного реєстру юридичних осіб та фізичних осіб-підприємців"
				: "Свідоцтво про державну реєстрацію";
		regNumber = user.getRegNumber();
		regDate = regDateFormat.format(user.getRegDate());
		GregorianCalendar cal = new GregorianCalendar();
		cal.clear();
		cal.set(2012, 1, 1);
		startDate = startDateFormat.format(cal.getTime());
		Address address = user.getAddress();
		if (address.getPostalCode() != null) {
			postalCode = String.format("%5d", address.getPostalCode());
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
		if (postalCode != null) {
			return "" + postalCode.charAt(index);
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

}
