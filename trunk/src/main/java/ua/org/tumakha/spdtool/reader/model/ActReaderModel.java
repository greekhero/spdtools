package ua.org.tumakha.spdtool.reader.model;

/**
 * @author Yuriy Tumakha
 */
public class ActReaderModel {

	private String lastname;

	private Integer salary;

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return String.format("ActReaderModel [lastname=%s, salary=%s]",
				lastname, salary);
	}

}
