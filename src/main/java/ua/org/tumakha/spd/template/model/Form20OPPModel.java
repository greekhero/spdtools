package ua.org.tumakha.spd.template.model;

import org.apache.commons.lang.StringUtils;

import ua.org.tumakha.spd.entity.Address;
import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.template.DocxTemplate.Template;

/**
 * @author Yuriy Tumakha
 */
public class Form20OPPModel extends TemplateModel {

	private String firstname;
	private String middlename;
	private String lastname;
	private String firstnameEn;
	private String lastnameEn;
	private String lastnameFirstMiddle;
	private String PIN;
	private String regDPI;
	private String regDPIspace;
	private String postalCode;
	private String region;
	private String district;
	private String city;
	private String street;
	private String house;
	private String slashHouse;
	private String apartment;

	public Form20OPPModel() {
	}

	public Form20OPPModel(User user) {
		copyProperties(user);
	}

	private void copyProperties(User user) {
		firstname = user.getFirstname();
		firstnameEn = user.getFirstnameEn();
		middlename = user.getMiddlename();
		lastname = user.getLastname();
		lastnameEn = user.getLastnameEn();
		lastnameFirstMiddle = String.format("%s   %s. %s.", user.getLastname(),
				user.getFirstname().substring(0, 1), user.getMiddlename()
						.substring(0, 1));
		PIN = user.getPIN().toString();
		if (user.getRegDPI() != null) {
			regDPI = user.getRegDPI();
			regDPIspace = StringUtils.repeat("_", (49 - regDPI.length()) / 2);
		}
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
		if (address.getSlashHouse() != null) {
			slashHouse = "" + address.getSlashHouse();
		}
		if (address.getApartment() != null) {
			apartment = "" + address.getApartment();
			if (address.getApartmentChar() != null) {
				apartment += "-" + address.getApartmentChar();
			}
		}
	}

	@Override
	public String getOutputFilename(Template template) {
		return String.format("/20-OPP/%s_%s_%s", lastnameEn, firstnameEn,
				template.getFilename());
	}

	private String pinAt(int index) {
		return "" + PIN.charAt(index);
	}

	public String getPinA() {
		return pinAt(0);
	}

	public String getPinB() {
		return pinAt(1);
	}

	public String getPinC() {
		return pinAt(2);
	}

	public String getPinD() {
		return pinAt(3);
	}

	public String getPinE() {
		return pinAt(4);
	}

	public String getPinF() {
		return pinAt(5);
	}

	public String getPinG() {
		return pinAt(6);
	}

	public String getPinH() {
		return pinAt(7);
	}

	public String getPinI() {
		return pinAt(8);
	}

	public String getPinJ() {
		return pinAt(9);
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

	public String getLastnameFirstMiddle() {
		return lastnameFirstMiddle;
	}

	public void setLastnameFirstMiddle(String lastnameFirstMiddle) {
		this.lastnameFirstMiddle = lastnameFirstMiddle;
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

	public String getRegDPIspace() {
		return regDPIspace;
	}

	public void setRegDPIspace(String regDPIspace) {
		this.regDPIspace = regDPIspace;
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

}
