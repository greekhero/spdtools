package ua.org.tumakha.spdtool.template.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ua.org.tumakha.spdtool.entity.BankTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yuriy Tumakha
 */
public class BankDayUSD {

    private static final Log log = LogFactory.getLog(BankDayUSD.class);
    private static final String BANK_DATE_FORMAT = "yyyyMMdd";
    private static final int INCOME_OPERATION_ID = 1;
    private static final int EXPENSE_OPERATION_ID = 0;

    private Integer date;
    private List<BankTransactionUSD> transactions = new ArrayList<BankTransactionUSD>();

    public BankDayUSD(Integer date, BankTransaction transaction) {
        this.date = date;
        addNewTransaction();
        addTransaction(transaction);
    }

    public void addTransaction(BankTransaction transaction) {
        boolean income = transaction.getOperationId().equals(INCOME_OPERATION_ID);
        boolean expense = transaction.getOperationId().equals(EXPENSE_OPERATION_ID);
        BankTransactionUSD lastTransaction = null;
        for (BankTransactionUSD usdTransaction : transactions) {
            if ((income && !usdTransaction.isIncome()) || (expense && !usdTransaction.isExpense())) {
                lastTransaction = usdTransaction;
                break;
            }
        }
        if (lastTransaction == null) {
            addNewTransaction();
            lastTransaction = getLastTransaction();
        }
        if (income) {
            lastTransaction.setIncome(transaction.getSumma(), transaction.getSummaEq());
        }
        if (expense) {
            lastTransaction.setExpense(transaction.getSumma(), transaction.getSummaEq());
        }
    }

    private void addNewTransaction() {
        transactions.add(new BankTransactionUSD(getDate()));
    }

    private BankTransactionUSD getLastTransaction() {
        return transactions.get(transactions.size() - 1);
    }

    public List<BankTransactionUSD> getTransactions() {
        return transactions;
    }

    public Integer getIntDate() {
        return date;
    }

    public Date getDate() {
        try {
            return new SimpleDateFormat(BANK_DATE_FORMAT).parse(date.toString());
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
