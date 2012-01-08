package ua.org.tumakha.spd.template.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.template.Template;

/**
 * @author Yuriy Tumakha
 */
public class TaxSystemStatementModel extends TemplateModel {

	private static final DateFormat startDateFormat = new SimpleDateFormat(
			"«dd»  MMMMM  yyyy", uaLocale);

	private String startDate;
	private String address;
	private String addressSpace;
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
		super(user);
		copyProperties(user);
	}

	private void copyProperties(User user) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.clear();
		cal.set(2012, Calendar.JANUARY, 1);
		startDate = startDateFormat.format(cal.getTime());
		address = "Україна, на замовлення";
		addressSpace = StringUtils.repeat("_", 90 - address.length());
	}

	@Override
	public String getOutputFilename(Template template) {
		return String.format("/Tax_System_Statement/%s_%s_%s", getLastnameEn(),
				getFirstnameEn(), template.getFilename());
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressSpace() {
		return addressSpace;
	}

	public void setAddressSpace(String addressSpace) {
		this.addressSpace = addressSpace;
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
