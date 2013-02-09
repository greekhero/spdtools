package ua.org.tumakha.spdtool.template.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.template.DocxTemplate;

/**
 * @author Yuriy Tumakha
 */
public class IncomeCalculationModel extends TemplateModel {

	private static final NumberFormat incomeFormat = getDecimalFormat();

	private String income;
	private String primaryReg;
	private String secondReg;

	public IncomeCalculationModel() {
	}

	public IncomeCalculationModel(User user) {
		super(user);
		// income = user.getIncome2011() != null ? incomeFormat.format(user
		// .getIncome2011()) : "";
		income = "";
		primaryReg = "+";
		secondReg = "";
	}

	@Override
	public String getOutputFilename(DocxTemplate template) {
		return String.format("/Tax_System_Statement/%s_%s_%s", getLastnameEn(),
				getFirstnameEn(), template.getFilename());
	}

	private static NumberFormat getDecimalFormat() {
		DecimalFormatSymbols forSpace = new DecimalFormatSymbols();
		forSpace.setGroupingSeparator(' ');
		return new DecimalFormat("###,###,###.00", forSpace);
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getPrimaryReg() {
		return primaryReg;
	}

	public void setPrimaryReg(String primaryReg) {
		this.primaryReg = primaryReg;
	}

	public String getSecondReg() {
		return secondReg;
	}

	public void setSecondReg(String secondReg) {
		this.secondReg = secondReg;
	}

}
