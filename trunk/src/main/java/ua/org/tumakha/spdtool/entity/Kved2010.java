package ua.org.tumakha.spdtool.entity;

import java.io.Serializable;

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
@Table(name = "kved2010")
public class Kved2010 implements Serializable {

	private static final long serialVersionUID = 1308660663772772774L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer kvedId;

	@Column(unique = true, nullable = false)
	private String code;

	private String name;

	private int priority;

	// @ManyToMany(mappedBy = "kveds")
	// private Set<User> users;

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

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

	@Override
	public String toString() {
		return "Kved2010 [kvedId=" + kvedId + ", code=" + code + ", name="
				+ name + "]";
	}

}
