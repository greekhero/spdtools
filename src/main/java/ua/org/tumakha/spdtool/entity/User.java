package ua.org.tumakha.spdtool.entity;

import java.io.Serializable;
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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import ua.org.tumakha.spdtool.enums.RegDocumentType;
import ua.org.tumakha.spdtool.enums.RentType;
import ua.org.tumakha.util.StrUtil;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1308009705873176089L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	@Version
	private Integer version;

	private Boolean active;

	@Column(length = 255)
	@NotNull
	@Size(max = 255)
	private String firstname;

	@Column(length = 255)
	@NotNull
	@Size(max = 255)
	private String firstnameEn;

	@Column(length = 255)
	@Size(max = 255)
	private String middlename;

	@Column(length = 255)
	@Size(max = 255)
	private String middlenameEn;

	@Column(length = 255)
	@NotNull
	@Size(min = 3, max = 255)
	private String lastname;

	@Column(length = 255)
	@NotNull
	@Size(min = 3, max = 255)
	private String lastnameEn;

	@Column(name = "PIN", unique = true, nullable = true)
	@Digits(integer = 10, fraction = 0)
	@Min(1000000000)
	@Max(9999999999L)
	private Long pin;

	@Enumerated(EnumType.ORDINAL)
	private RegDocumentType regDocumentType;

	@Pattern(regexp = "\\d \\d{3} \\d{3} \\d{4} \\d{6}", message = "Format not match (# ### ### #### ######)")
	private String regNumber;

	@Column(name = "regDate", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	@Past
	private Date regDate;

	@Column(name = "regNumberDPI")
	private Long regNumberDpi;

	@Column(name = "regDateDPI", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	@Past
	private Date regDateDpi;

	@Column(length = 20)
	private String regNumberPF;

	@ManyToOne
	@JoinColumn(name = "taxOrganizationId", nullable = true)
	private TaxOrganization taxOrganization;

	@ManyToOne
	@JoinColumn(name = "pensionOrganizationId", nullable = true)
	private PensionOrganization pensionOrganization;

	@Enumerated(EnumType.STRING)
	private RentType rentType;

	@Column(length = 20)
	private String rentContractNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	private Date rentContractDate;

	private Long phone;

	@Email
	private String email;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Address address;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Bank bank;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private ServiceType serviceType;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Passport passport;

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
	@OrderBy("priority DESC")
	private List<Kved> kveds;

	@ManyToMany
	@JoinTable(name = "user_active_kved_mapping", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "kvedId", referencedColumnName = "kvedId"))
	@OrderBy("priority DESC")
	private List<Kved2010> activeKveds;

	@ManyToMany
	@JoinTable(name = "user_kved2010_mapping", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "kvedId", referencedColumnName = "kvedId"))
	@OrderBy("priority DESC")
	private List<Kved2010> kveds2010;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("year desc, quarter desc")
	private List<Declaration> declarations;

	public String getPinStr() {
		return getPin().toString();
	}

	public char[] getPinArray() {
		return getPin().toString().toCharArray();
	}

	public String getEmailStr() {
		return StrUtil.padRight(email, 60);
	}

	public String getFullname() {
		return String.format("%s %s %s", getLastname(), getFirstname(), getMiddlename());
	}

	public String getLastnameFirstMiddle() {
		return String.format("%s  %s. %s.", getLastname(), getFirstname().substring(0, 1),
				getMiddlename().substring(0, 1));
	}

	public String getFirstMiddleLastname() {
		return StrUtil.padRight(String.format("%s.%s.  %s", getFirstname().substring(0, 1),
				getMiddlename().substring(0, 1), getLastname()), 72);
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

	public RegDocumentType getRegDocumentType() {
		return regDocumentType;
	}

	public void setRegDocumentType(RegDocumentType regDocumentType) {
		this.regDocumentType = regDocumentType;
	}

	public Long getRegNumberDpi() {
		return regNumberDpi;
	}

	public void setRegNumberDpi(Long regNumberDpi) {
		this.regNumberDpi = regNumberDpi;
	}

	public Date getRegDateDpi() {
		return regDateDpi;
	}

	public void setRegDateDpi(Date regDateDpi) {
		this.regDateDpi = regDateDpi;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getRegNumberPF() {
		return regNumberPF;
	}

	public void setRegNumberPF(String regNumberPF) {
		this.regNumberPF = regNumberPF;
	}

	public TaxOrganization getTaxOrganization() {
		return taxOrganization;
	}

	public void setTaxOrganization(TaxOrganization taxOrganization) {
		this.taxOrganization = taxOrganization;
	}

	public PensionOrganization getPensionOrganization() {
		return pensionOrganization;
	}

	public void setPensionOrganization(PensionOrganization pensionOrganization) {
		this.pensionOrganization = pensionOrganization;
	}

	public RentType getRentType() {
		return rentType;
	}

	public void setRentType(RentType rentType) {
		this.rentType = rentType;
	}

	public String getRentContractNumber() {
		return rentContractNumber;
	}

	public void setRentContractNumber(String rentContractNumber) {
		this.rentContractNumber = rentContractNumber;
	}

	public Date getRentContractDate() {
		return rentContractDate;
	}

	public void setRentContractDate(Date rentContractDate) {
		this.rentContractDate = rentContractDate;
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
		if (address != null && address.getUser() == null) {
			address.setUser(this);
		}
		this.address = address;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		if (bank != null && bank.getUser() == null) {
			bank.setUser(this);
		}

		this.bank = bank;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		if (serviceType != null && serviceType.getUser() == null) {
			serviceType.setUser(this);
		}
		this.serviceType = serviceType;
	}

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		if (passport != null && passport.getUser() == null) {
			passport.setUser(this);
		}
		this.passport = passport;
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

	public List<Kved2010> getActiveKveds() {
		return activeKveds;
	}

	public void setActiveKveds(List<Kved2010> activeKveds) {
		this.activeKveds = activeKveds;
	}

	public List<Kved2010> getKveds2010() {
		return kveds2010;
	}

	public void setKveds2010(List<Kved2010> kveds2010) {
		this.kveds2010 = kveds2010;
	}

	public List<Declaration> getDeclarations() {
		return declarations;
	}

	public void setDeclarations(List<Declaration> declarations) {
		this.declarations = declarations;
	}

}
