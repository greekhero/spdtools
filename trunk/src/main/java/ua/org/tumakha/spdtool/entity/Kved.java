package ua.org.tumakha.spdtool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "kved")
public class Kved {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer kvedId;

	@Column(unique = true, nullable = false)
	private String code;

	private String name;

	private int priority;

	// @ManyToMany(mappedBy = "kveds")
	// private Set<User> users;

	public Integer getKvedId() {
		return kvedId;
	}

	public void setKvedId(Integer kvedId) {
		this.kvedId = kvedId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Kved [kvedId=" + kvedId + ", code=" + code + ", name=" + name
				+ "]";
	}

}
