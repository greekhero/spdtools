package ua.org.tumakha.spdtool.template.model;

import java.util.Date;

/**
 * @author Yuriy Tumakha
 */
public class BankTransactionUSD {

    private Date date;

    private Integer incomeAmountUSD;

    private Integer incomeAmountUAH;

    private Integer expenseAmountUSD;

    private Integer expenseAmountUAH;

    public BankTransactionUSD(Date date) {
        this.date = date;
    }

    public boolean isIncome() {
        return incomeAmountUSD != null;
    }

    public boolean isExpense() {
        return expenseAmountUSD != null;
    }

    public boolean isIncomeAndExpense() {
        return isIncome() && isExpense();
    }

    public Double getCurrencyRate() {
        Double rate = null;
        if (isIncome()) {
            rate = (double) incomeAmountUAH / incomeAmountUSD;
        } else if (isExpense()) {
            rate = (double) expenseAmountUAH / expenseAmountUSD;
        }
        if (rate != null) {
            rate = (double) (int) (rate * 10000) / 10000;
        }
        return rate;
    }

    public String getDescr() {
        StringBuffer sb = new StringBuffer();
        if (isIncome()) {
            sb.append("зарах");
        }
        if (isIncomeAndExpense()) {
            sb.append("/");
        }
        if (isExpense()) {
            sb.append("прод");
        }
        return sb.toString();
    }

    public void setIncome(Integer incomeAmountUSD, Integer incomeAmountUAH) {
        this.incomeAmountUSD = incomeAmountUSD;
        this.incomeAmountUAH = incomeAmountUAH;
    }

    public void setExpense(Integer expenseAmountUSD, Integer expenseAmountUAH) {
        this.expenseAmountUSD = expenseAmountUSD;
        this.expenseAmountUAH = expenseAmountUAH;
    }

    public Date getDate() {
        return date;
    }

    public Double getIncomeAmountUSD() {
        return incomeAmountUSD == null ? 0 : (double) incomeAmountUSD / 100;
        //return incomeAmountUSD == null ? null : (double) incomeAmountUSD / 100;
    }

    public Double getIncomeAmountUAH() {
        return incomeAmountUAH == null ? 0 : (double) incomeAmountUAH / 100;
        //return incomeAmountUAH == null ? null : (double) incomeAmountUAH / 100;
    }

    public Double getExpenseAmountUSD() {
        return expenseAmountUSD == null ? 0 : (double) expenseAmountUSD / 100;
        //return expenseAmountUSD == null ? null : (double) expenseAmountUSD / 100;
    }

    public Double getExpenseAmountUAH() {
        return expenseAmountUAH == null ? 0 : (double) expenseAmountUAH / 100;
        //return expenseAmountUAH == null ? null : (double) expenseAmountUAH / 100;
    }

}
