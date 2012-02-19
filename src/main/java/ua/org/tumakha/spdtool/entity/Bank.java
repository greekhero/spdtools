package ua.org.tumakha.spdtool.entity;

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
@Table(name = "bank")
public class Bank {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer bankId;

	@OneToOne
	@JoinColumn(name = "userId", unique = true, nullable = false, updatable = false)
	private User user;

	@Column(nullable = false)
	private Long accountNumber;

	private String name;

	private String nameEn;

	private Integer MFO;

	private String SWIFT;

	@Column(length = 1024)
	private String correspondentBank;

	@Column(length = 1024)
	private String correspondentBankEn;

	private boolean usedMiddlename;

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
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

	public Integer getMFO() {
		return MFO;
	}

	public void setMFO(Integer mFO) {
		MFO = mFO;
	}

	public String getSWIFT() {
		return SWIFT;
	}

	public void setSWIFT(String sWIFT) {
		SWIFT = sWIFT;
	}

	public String getCorrespondentBank() {
		return correspondentBank;
	}

	public void setCorrespondentBank(String correspondentBank) {
		this.correspondentBank = correspondentBank;
	}

	public String getCorrespondentBankEn() {
		return correspondentBankEn;
	}

	public void setCorrespondentBankEn(String correspondentBankEn) {
		this.correspondentBankEn = correspondentBankEn;
	}

	public boolean isUsedMiddlename() {
		return usedMiddlename;
	}

	public void setUsedMiddlename(boolean usedMiddlename) {
		this.usedMiddlename = usedMiddlename;
	}

}