package ua.org.tumakha.spdtool.reader.model;

import java.math.BigDecimal;

/**
 * @author Yuriy Tumakha
 */
public class DeclarationReaderModel {

	private String lastname;

	private String firstname;

	private BigDecimal income;

	private BigDecimal tax;

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	@Override
	public String toString() {
		return String.format("DeclarationReaderModel [lastname=%s, income=%s, tax=%s]", lastname, income, tax);
	}

}
