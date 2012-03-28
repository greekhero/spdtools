package ua.org.tumakha.spdtool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "declaration", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"userId", "year", "quarter" }) })
public class Declaration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer declarationId;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false, updatable = false)
	private User user;

	@Column(nullable = false)
	@NotNull
	private Integer year;

	@Column(nullable = false)
	@NotNull
	private Integer quarter;

	@Column(precision = 0)
	private Float income;

	@Column(precision = 0)
	private Float tax;

	public Integer getDeclarationId() {
		return declarationId;
	}

	public void setDeclarationId(Integer declarationId) {
		this.declarationId = declarationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getQuarter() {
		return quarter;
	}

	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}

	public Float getIncome() {
		return income;
	}

	public void setIncome(Float income) {
		this.income = income;
	}

	public Float getTax() {
		return tax;
	}

	public void setTax(Float tax) {
		this.tax = tax;
	}

}
