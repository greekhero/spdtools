package ua.org.tumakha.spdtool.entity;

import java.io.Serializable;
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

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "contract", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"number", "userId" }) })
public class Contract implements Serializable {

	private static final long serialVersionUID = 1308373430456843318L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer contractId;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@Column(nullable = false)
	private String number;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
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
