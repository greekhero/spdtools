package ua.org.tumakha.spdtool.reader.model;

/**
 * @author Yuriy Tumakha
 */
public class DeclarationReaderModel {

	private String lastname;

	private Integer income;

	private Integer tax;

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public Integer getTax() {
		return tax;
	}

	public void setTax(Integer tax) {
		this.tax = tax;
	}

	@Override
	public String toString() {
		return String.format(
				"DeclarationReaderModel [lastname=%s, income=%s, tax=%s]",
				lastname, income, tax);
	}

}
