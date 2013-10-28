package ua.org.tumakha.spdtool.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "bank")
public class Bank implements Serializable {

	private static final long serialVersionUID = 1308052916445429074L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer bankId;

	@OneToOne
	@JoinColumn(name = "userId", unique = true, nullable = false, updatable = false)
	private User user;

	@Column(nullable = true)
	private Long accountNumber;

	private String name;

	private String nameEn;

	@Digits(integer = 6, fraction = 0)
	@Min(100000)
	@Max(999999)
	private Integer MFO;

	private String SWIFT;

	@Column(length = 1024)
	private String correspondentBank;

	@Column(length = 1024)
	private String correspondentBankEn;

    @Column(columnDefinition = "bit")
    @Type(type = "org.hibernate.type.NumericBooleanType")
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
