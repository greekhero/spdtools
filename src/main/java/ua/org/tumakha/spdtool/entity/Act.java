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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "act", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"number", "userId" }) })
public class Act implements Serializable {

	private static final long serialVersionUID = 1308649270136623926L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer actId;

	@ManyToOne
	@JoinColumn(name = "contractId", nullable = false, updatable = false)
	private Contract contract;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false, updatable = false)
	private User user;

	@Column(length = 255, nullable = false)
	@NotNull
	private String number;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date dateFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date dateTo;

	@Column
	private Integer amount;

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
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

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
