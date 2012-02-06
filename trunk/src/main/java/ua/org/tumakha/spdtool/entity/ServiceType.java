package ua.org.tumakha.spdtool.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "service_type")
public class ServiceType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer serviceTypeId;

	@OneToOne
	@JoinColumn(name = "userId", unique = true, nullable = false, updatable = false)
	private User user;

	private String name;

	private String nameEn;

	public Integer getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNameVOrudnomu() {
		return name.replace("послуги ", "послуг ")
				.replace("комерційні ", "комерційних ")
				.replace("секретарські ", "секретарських ");
	}

	public String getName() {
		return name;
	}

	public void setName(String bankName) {
		name = bankName;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String bankNameEn) {
		nameEn = bankNameEn;
	}

}
