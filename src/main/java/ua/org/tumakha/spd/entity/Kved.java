package ua.org.tumakha.spd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "kved")
public class Kved {

	@Id
	@GeneratedValue
	private Integer kvedId;

	@Column(unique = true, nullable = false)
	private String code;

	private String name;

	// @ManyToMany(mappedBy = "kveds")
	// @JoinTable(name = "user_kved_mapping", joinColumns = @JoinColumn(name =
	// "kvedId", referencedColumnName = "kvedId"), inverseJoinColumns =
	// @JoinColumn(name = "userId", referencedColumnName = "userId"))
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

}
