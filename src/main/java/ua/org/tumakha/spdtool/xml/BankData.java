package ua.org.tumakha.spdtool.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ua.org.tumakha.spdtool.entity.BankTransaction;

/**
 * @author Yuriy Tumakha
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROWDATA")
public class BankData {

	@XmlElement(name = "ROW", required = true)
	private List<BankTransaction> bankTransactions;

	public List<BankTransaction> getBankTransactions() {
		return bankTransactions;
	}

	public void setBankTransactions(final List<BankTransaction> bankTransactions) {
		this.bankTransactions = bankTransactions;
	}

}
