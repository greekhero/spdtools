package ua.org.tumakha.spd.template.model;

import org.apache.commons.lang.StringUtils;

import ua.org.tumakha.spd.entity.Address;
import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.template.DocxTemplate;

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
		lastnameFirstMiddle = user.getLastnameFirstMiddle();
		PIN = user.getPin().toString();
		if (user.getRegDpi() != null) {
			regDPI = user.getRegDpi();
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
		slashHouse = address.getSlashHouse() != null ? ""
				+ address.getSlashHouse() : "";
		if (address.getApartment() != null) {
			apartment = "" + address.getApartment();
			if (address.getApartmentChar() != null) {
				apartment += "-" + address.getApartmentChar();
			}
		}
	}

	@Override
	public String getOutputFilename(DocxTemplate template) {
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

	@Override
	public String getFirstname() {
		return firstname;
	}

	@Override
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public String getMiddlename() {
		return middlename;
	}

	@Override
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	@Override
	public String getLastname() {
		return lastname;
	}

	@Override
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String getFirstnameEn() {
		return firstnameEn;
	}

	@Override
	public void setFirstnameEn(String firstnameEn) {
		this.firstnameEn = firstnameEn;
	}

	@Override
	public String getLastnameEn() {
		return lastnameEn;
	}

	@Override
	public void setLastnameEn(String lastnameEn) {
		this.lastnameEn = lastnameEn;
	}

	public String getLastnameFirstMiddle() {
		return lastnameFirstMiddle;
	}

	public void setLastnameFirstMiddle(String lastnameFirstMiddle) {
		this.lastnameFirstMiddle = lastnameFirstMiddle;
	}

	@Override
	public String getPIN() {
		return PIN;
	}

	@Override
	public void setPIN(String pIN) {
		PIN = pIN;
	}

	@Override
	public String getRegDPI() {
		return regDPI;
	}

	@Override
	public void setRegDPI(String regDPI) {
		this.regDPI = regDPI;
	}

	public String getRegDPIspace() {
		return regDPIspace;
	}

	public void setRegDPIspace(String regDPIspace) {
		this.regDPIspace = regDPIspace;
	}

	@Override
	public String getPostalCode() {
		return postalCode;
	}

	@Override
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String getDistrict() {
		return district;
	}

	@Override
	public void setDistrict(String district) {
		this.district = district;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getStreet() {
		return street;
	}

	@Override
	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String getHouse() {
		return house;
	}

	@Override
	public void setHouse(String house) {
		this.house = house;
	}

	@Override
	public String getSlashHouse() {
		return slashHouse;
	}

	@Override
	public void setSlashHouse(String slashHouse) {
		this.slashHouse = slashHouse;
	}

	@Override
	public String getApartment() {
		return apartment;
	}

	@Override
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

}
