package ua.org.tumakha.spd.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import ua.org.tumakha.spd.enums.RegDocumentType;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue
	private Integer userId;

	@Version
	private Integer version;

	private Boolean active;

	private String firstname;

	private String firstnameEn;

	private String middlename;

	private String middlenameEn;

	private String lastname;

	private String lastnameEn;

	@Column(unique = true, nullable = true)
	private Long pin;

	@Enumerated(EnumType.ORDINAL)
	private RegDocumentType regDocumentType;

	private String regNumber;

	private Date regDate;

	private String regDpi;

	private Integer regNumberDpi;

	private Date regDateDpi;

	private Long phone;

	private String email;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Address address;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Bank bank;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private ServiceType serviceType;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("date desc")
	private List<Contract> contracts;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("dateTo desc")
	private List<Act> acts;

	@ManyToMany
	@JoinTable(name = "user_group_mapping", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "groupId", referencedColumnName = "groupId"))
	private Set<Group> groups;

	@ManyToMany
	@JoinTable(name = "user_kved_mapping", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "kvedId", referencedColumnName = "kvedId"))
	@OrderBy("code")
	private List<Kved> kveds;

	@Column
	private Integer income2011;

	public char[] getPinArray() {
		return getPin().toString().toCharArray();
	}

	public String getLastnameFirstMiddle() {
		return String.format("%s  %s. %s.", getLastname(), getFirstname()
				.substring(0, 1), getMiddlename().substring(0, 1));
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean isActive() {
		if (active != null) {
			return active;
		}
		return false;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeleted() {
		return active == null;
	}

	public void setDeleted(boolean deleted) {
		if (deleted) {
			active = null;
		}
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFirstnameEn() {
		return firstnameEn;
	}

	public void setFirstnameEn(String firstnameEn) {
		this.firstnameEn = firstnameEn;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getMiddlenameEn() {
		return middlenameEn;
	}

	public void setMiddlenameEn(String middlenameEn) {
		this.middlenameEn = middlenameEn;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLastnameEn() {
		return lastnameEn;
	}

	public void setLastnameEn(String lastnameEn) {
		this.lastnameEn = lastnameEn;
	}

	public Long getPin() {
		return pin;
	}

	public void setPin(Long pin) {
		this.pin = pin;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getRegDpi() {
		return regDpi;
	}

	public void setRegDpi(String regDpi) {
		this.regDpi = regDpi;
	}

	public RegDocumentType getRegDocumentType() {
		return regDocumentType;
	}

	public void setRegDocumentType(RegDocumentType regDocumentType) {
		this.regDocumentType = regDocumentType;
	}

	public Integer getRegNumberDpi() {
		return regNumberDpi;
	}

	public void setRegNumberDpi(Integer regNumberDpi) {
		this.regNumberDpi = regNumberDpi;
	}

	public Date getRegDateDpi() {
		return regDateDpi;
	}

	public void setRegDateDpi(Date regDateDpi) {
		this.regDateDpi = regDateDpi;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public List<Act> getActs() {
		return acts;
	}

	public void setActs(List<Act> acts) {
		this.acts = acts;
	}

	public Contract getLastContract() {
		if (contracts != null && contracts.size() > 0) {
			return contracts.get(0);
		}
		return null;
	}

	public Act getLastAct() {
		if (acts != null && acts.size() > 0) {
			return acts.get(0);
		}
		return null;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public List<Kved> getKveds() {
		return kveds;
	}

	public void setKveds(List<Kved> kveds) {
		this.kveds = kveds;
	}

	public Integer getIncome2011() {
		return income2011;
	}

	public void setIncome2011(Integer income2011) {
		this.income2011 = income2011;
	}

}
