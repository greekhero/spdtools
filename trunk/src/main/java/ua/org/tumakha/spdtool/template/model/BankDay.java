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
public class BankDay {

    private static final Log log = LogFactory.getLog(BankDay.class);
    private static final String BANK_DATE_FORMAT = "yyyyMMdd";
    private static final String USD_CURRENCY = "USD";
    private static final String UAH_CURRENCY = "UAH";
    private static final int INCOME_OPERATION_ID = 1;
    private static final int EXPENSE_OPERATION_ID = 0;

    private Integer date;
    private List<BankOperationRow> transactions = new ArrayList<BankOperationRow>();

    public BankDay(Integer date, BankTransaction transaction) {
        this.date = date;
        addNewOperationRow();
        addTransaction(transaction);
    }

    public void addTransaction(BankTransaction transaction) {
        boolean isAccountUAH = UAH_CURRENCY.equals(transaction.getCurrSymbolCode());
        boolean isAccountUSD = USD_CURRENCY.equals(transaction.getCurrSymbolCode());
        boolean income = transaction.getOperationId().equals(INCOME_OPERATION_ID);
        boolean expense = transaction.getOperationId().equals(EXPENSE_OPERATION_ID);
        BankOperationRow lastOperationRow = null;
        for (BankOperationRow operationRow : transactions) {
            if ((isAccountUSD && income && !operationRow.isIncomeUSD())
                    || (isAccountUSD && expense && !operationRow.isExpenseUSD())
                    || (isAccountUAH && !operationRow.isAccountUAH())) {
                lastOperationRow = operationRow;
                break;
            }
        }
        if (lastOperationRow == null) {
            addNewOperationRow();
            lastOperationRow = getLastOperationRow();
        }
        if (isAccountUAH) {
            if (income) {
                lastOperationRow.setIncomeUAH(transaction.getPlatPurpose(), transaction.getSumma());
            }
            if (expense) {
                lastOperationRow.setExpenseUAH(transaction.getPlatPurpose(), transaction.getSumma());
            }
        }
        if (isAccountUSD) {
            if (income) {
                lastOperationRow.setIncomeUSD(transaction.getSumma(), transaction.getSummaEq());
            }
            if (expense) {
                lastOperationRow.setExpenseUSD(transaction.getSumma(), transaction.getSummaEq());
            }
        }
    }

    private void addNewOperationRow() {
        transactions.add(new BankOperationRow(getDate()));
    }

    private BankOperationRow getLastOperationRow() {
        return transactions.get(transactions.size() - 1);
    }

    public List<BankOperationRow> getTransactions() {
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
