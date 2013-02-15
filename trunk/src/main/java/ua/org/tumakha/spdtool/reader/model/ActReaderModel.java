package ua.org.tumakha.spdtool.reader.model;

/**
 * @author Yuriy Tumakha
 */
public class ActReaderModel {

	private String lastname;

	private String firstname;

	private Integer salary;

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

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return String.format("ActReaderModel [lastname=%s, firstname=%s, salary=%s]", lastname, firstname, salary);
	}

}
