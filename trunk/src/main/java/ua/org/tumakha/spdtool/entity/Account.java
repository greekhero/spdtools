package ua.org.tumakha.spdtool.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 130844457010690633L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer accountId;

	private Long accountNumber;

	private Long codeEDRPOU;

	@Digits(integer = 6, fraction = 0)
	@Min(100000)
	@Max(999999)
	private Integer MFO;

	@Column(length = 255)
	private String bankName;

	@Column(length = 255)
	private String payeeName;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getCodeEDRPOU() {
		return codeEDRPOU;
	}

	public void setCodeEDRPOU(Long codeEDRPOU) {
		this.codeEDRPOU = codeEDRPOU;
	}

	public Integer getMFO() {
		return MFO;
	}

	public void setMFO(Integer mFO) {
		MFO = mFO;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

}
