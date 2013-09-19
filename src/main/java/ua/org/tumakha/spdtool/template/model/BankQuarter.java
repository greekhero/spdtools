package ua.org.tumakha.spdtool.template.model;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuriy Tumakha
 */
public class BankQuarter {

    private Integer number;
    private List<BankOperationRow> transactions = new ArrayList<BankOperationRow>();

    public BankQuarter(int number) {
        this.number = number;
    }

    public void addTransaction(BankOperationRow transaction) {
        transactions.add(transaction);
    }

    public Integer getNumber() {
        return number;
    }

    public String getRomanNumber() {
        return number == 4 ? "IV" : StringUtils.repeat("I", number);
    }

    public List<BankOperationRow> getTransactions() {
        return transactions;
    }

}
