package ua.org.tumakha.spdtool.template.model;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuriy Tumakha
 */
public class BankQuarterUSD {

    private Integer number;
    private List<BankTransactionUSD> transactions = new ArrayList<BankTransactionUSD>();

    public BankQuarterUSD(int number) {
        this.number = number;
    }

    public void addDay(BankTransactionUSD transaction) {
        transactions.add(transaction);
    }

    public Integer getNumber() {
        return number;
    }

    public String getRomanNumber() {
        return number == 4 ? "IV" : StringUtils.repeat("I", number);
    }

    public List<BankTransactionUSD> getTransactions() {
        return transactions;
    }

}
