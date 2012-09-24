package ua.org.tumakha.spdtool.reader.model;

/**
 * @author Yuriy Tumakha
 */
public class DeclarationReaderModel {

	private String lastname;

	private Float income;

	private Float tax;

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Float getIncome() {
		return income;
	}

	public void setIncome(Float income) {
		this.income = income;
	}

	public Float getTax() {
		return tax;
	}

	public void setTax(Float tax) {
		this.tax = tax;
	}

	@Override
	public String toString() {
		return String.format(
				"DeclarationReaderModel [lastname=%s, income=%s, tax=%s]",
				lastname, income, tax);
	}

}
