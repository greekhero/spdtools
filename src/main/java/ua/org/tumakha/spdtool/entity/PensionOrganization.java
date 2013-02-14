package ua.org.tumakha.spdtool.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "pension_organization")
public class PensionOrganization implements Serializable {

	private static final long serialVersionUID = 1308819930628893983L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer organizationId;

	@Column(unique = true, length = 255)
	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "accountId", unique = true, nullable = false, updatable = false)
	private Account account;

	@OneToMany(mappedBy = "pensionOrganization", fetch = FetchType.LAZY)
	@OrderBy("active desc, lastname asc")
	private Set<User> users;

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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getPayeeName() {
		return account.getPayeeName();
	}

	public Long getAccountNumber() {
		return account.getAccountNumber();
	}

}
