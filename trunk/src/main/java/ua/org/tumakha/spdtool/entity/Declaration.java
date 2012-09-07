package ua.org.tumakha.spdtool.entity;

import java.io.Serializable;

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
public class Declaration implements Serializable {

	private static final long serialVersionUID = 1308357222957583032L;

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
	private Integer income;

	@Column(precision = 0)
	private Integer tax;

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

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public Integer getTax() {
		return tax;
	}

	public void setTax(Integer tax) {
		this.tax = tax;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Declaration [");
		if (declarationId != null) {
			builder.append("declarationId=");
			builder.append(declarationId);
			builder.append(", ");
		}
		if (user != null) {
			builder.append("user=");
			builder.append(user);
			builder.append(", ");
		}
		if (year != null) {
			builder.append("year=");
			builder.append(year);
			builder.append(", ");
		}
		if (quarter != null) {
			builder.append("quarter=");
			builder.append(quarter);
			builder.append(", ");
		}
		if (income != null) {
			builder.append("income=");
			builder.append(income);
			builder.append(", ");
		}
		if (tax != null) {
			builder.append("tax=");
			builder.append(tax);
		}
		builder.append("]");
		return builder.toString();
	}

}