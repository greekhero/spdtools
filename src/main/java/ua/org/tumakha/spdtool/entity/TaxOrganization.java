package ua.org.tumakha.spdtool.entity;

import java.io.Serializable;

import javax.persistence.Column;
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
@Table(name = "tax_organization")
public class TaxOrganization implements Serializable {

	private static final long serialVersionUID = 1308919951907524084L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer organizationId;

	@Column(length = 255)
	private String name;

	private Integer code;

	@OneToOne
	@JoinColumn(name = "accountId", unique = true, nullable = false, updatable = false)
	private Account account;

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
