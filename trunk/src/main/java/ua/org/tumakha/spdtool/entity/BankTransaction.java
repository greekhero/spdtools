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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "bank_transaction", uniqueConstraints = { @UniqueConstraint(columnNames = { "id", "userId" }) })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROW")
public class BankTransaction implements Serializable {

	private static final long serialVersionUID = 1308052953445429074L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlTransient
	private Integer bankTransactionId;

	@OneToOne
	@JoinColumn(name = "userId", nullable = false, updatable = false)
	@XmlTransient
	private User user;

	@XmlAttribute(name = "ID")
	private Integer id;

	@XmlAttribute(name = "ARCDATE")
	private Integer arcDate;

	@XmlAttribute(name = "OPERATIONID")
	private Integer operationId;

	@Column(length = 3)
	@XmlAttribute(name = "CURRSYMBOLCODE")
	private String currSymbolCode;

	@Column(length = 100)
	@XmlAttribute(name = "DOCUMENTNO")
	private String documentNo;

	@Column(length = 100)
	@XmlAttribute(name = "DOCUMENTDATE")
	private String documentDate;

	@XmlAttribute(name = "SUMMA")
	private Integer summa;

	@Column(length = 100)
	@XmlAttribute(name = "BOOKEDDATE")
	private String bookedDate;

	@XmlAttribute(name = "CURRENCYID")
	private Integer currencyId;

	@XmlAttribute(name = "IsDeleted")
	private Boolean isDeleted;

	@Column(length = 100)
	@XmlAttribute(name = "DOCSUBTYPESNAME")
	private String docSubTypesName;

	@Column(length = 255)
	@XmlAttribute(name = "PLATPURPOSE")
	private String platPurpose;

	@XmlAttribute(name = "DOCUMENTTYPEID")
	private Integer documentTypeId;

	@Column(length = 100)
	@XmlAttribute(name = "BANKDATE")
	private String bankDate;

	@Column(length = 255)
	@XmlAttribute(name = "CORRBANKNAME")
	private String corrBankName;

	@Column(length = 255)
	@XmlAttribute(name = "CORRCONTRAGENTSNAME")
	private String corrContragentsName;

	@XmlAttribute(name = "CORRACCOUNTNO")
	private Long corrAccountNo;

	@XmlAttribute(name = "CORRBANKID")
	private Integer corrBankId;

	@XmlAttribute(name = "CORRIDENTIFYCODE")
	private Long corrIdentifyCode;

	@XmlAttribute(name = "BANKID")
	private Integer bankId;

	@XmlAttribute(name = "ACCOUNTNO")
	private Long accountNo;

	@Column(length = 255)
	@XmlAttribute(name = "BANKNAME")
	private String bankName;

	@XmlAttribute(name = "IDENTIFYCODE")
	private Long identifyCode;

	@Column(length = 100)
	@XmlAttribute(name = "ACCDESCR")
	private String accDescr;

	@Column(length = 255)
	@XmlAttribute(name = "CONTRAGENTSNAME")
	private String contrAgentsName;

	@XmlAttribute(name = "ACCOUNTID")
	private Integer accountId;

	public Integer getBankTransactionId() {
		return bankTransactionId;
	}

	public void setBankTransactionId(Integer bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getArcDate() {
		return arcDate;
	}

	public void setArcDate(final Integer arcDate) {
		this.arcDate = arcDate;
	}

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(final Integer operationId) {
		this.operationId = operationId;
	}

	public String getCurrSymbolCode() {
		return currSymbolCode;
	}

	public void setCurrSymbolCode(final String currSymbolCode) {
		this.currSymbolCode = currSymbolCode;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(final String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(final String documentDate) {
		this.documentDate = documentDate;
	}

	public Integer getSumma() {
		return summa;
	}

	public void setSumma(final Integer summa) {
		this.summa = summa;
	}

	public String getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(final String bookedDate) {
		this.bookedDate = bookedDate;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(final Integer currencyId) {
		this.currencyId = currencyId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(final Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getDocSubTypesName() {
		return docSubTypesName;
	}

	public void setDocSubTypesName(final String docSubTypesName) {
		this.docSubTypesName = docSubTypesName;
	}

	public String getPlatPurpose() {
		return platPurpose;
	}

	public void setPlatPurpose(final String platPurpose) {
		this.platPurpose = platPurpose;
	}

	public Integer getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(final Integer documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getBankDate() {
		return bankDate;
	}

	public void setBankDate(final String bankDate) {
		this.bankDate = bankDate;
	}

	public String getCorrBankName() {
		return corrBankName;
	}

	public void setCorrBankName(final String corrBankName) {
		this.corrBankName = corrBankName;
	}

	public String getCorrContragentsName() {
		return corrContragentsName;
	}

	public void setCorrContragentsName(final String corrContragentsName) {
		this.corrContragentsName = corrContragentsName;
	}

	public Long getCorrAccountNo() {
		return corrAccountNo;
	}

	public void setCorrAccountNo(final Long corrAccountNo) {
		this.corrAccountNo = corrAccountNo;
	}

	public Integer getCorrBankId() {
		return corrBankId;
	}

	public void setCorrBankId(final Integer corrBankId) {
		this.corrBankId = corrBankId;
	}

	public Long getCorrIdentifyCode() {
		return corrIdentifyCode;
	}

	public void setCorrIdentifyCode(final Long corrIdentifyCode) {
		this.corrIdentifyCode = corrIdentifyCode;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(final Integer bankId) {
		this.bankId = bankId;
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(final Long accountNo) {
		this.accountNo = accountNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(final String bankName) {
		this.bankName = bankName;
	}

	public Long getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(final Long identifyCode) {
		this.identifyCode = identifyCode;
	}

	public String getAccDescr() {
		return accDescr;
	}

	public void setAccDescr(final String accDescr) {
		this.accDescr = accDescr;
	}

	public String getContrAgentsName() {
		return contrAgentsName;
	}

	public void setContrAgentsName(final String contrAgentsName) {
		this.contrAgentsName = contrAgentsName;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(final Integer accountId) {
		this.accountId = accountId;
	}

}