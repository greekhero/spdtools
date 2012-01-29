package ua.org.tumakha.spd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "contract", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"number", "userId" }) })
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer contractId;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@Column(nullable = false)
	private String number;

	@Column(nullable = false)
	private Date date;

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
