package ua.org.tumakha.spdtool.template.model;

import ua.org.tumakha.util.PaymentPurposeUtil;

import java.util.Date;

/**
 * @author Yuriy Tumakha
 */
public class BankOperationRow {

    private Date date;

    // USD account
    private Integer incomeAmountUSD;
    private Integer incomeAmountUAH;
    private Integer expenseAmountUSD;
    private Integer expenseAmountUAH;

    // UAH account
    private String purpose;
    private String originalPurpose;
    private Integer income;
    private Integer expense;
    private Integer commission;

    public BankOperationRow(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public boolean isIncomeUSD() {
        return incomeAmountUSD != null;
    }

    public boolean isExpenseUSD() {
        return expenseAmountUSD != null;
    }

    public boolean isIncomeAndExpenseUSD() {
        return isIncomeUSD() && isExpenseUSD();
    }

    public boolean isAccountUAH() {
        return purpose != null;
    }

    public Double getCurrencyRate() {
        Double rate = 0d;
        if (isIncomeUSD()) {
            rate = (double) incomeAmountUAH / incomeAmountUSD;
        } else if (isExpenseUSD()) {
            rate = (double) expenseAmountUAH / expenseAmountUSD;
        }
        return (double) (int) (rate * 10000) / 10000;
    }

    public String getDescr() {
        StringBuffer sb = new StringBuffer();
        if (isIncomeUSD()) {
            sb.append("зарах");
        }
        if (isIncomeAndExpenseUSD()) {
            sb.append("/");
        }
        if (isExpenseUSD()) {
            sb.append("прод");
        }
        return sb.toString();
    }

    public void setIncomeUSD(Integer incomeAmountUSD, Integer incomeAmountUAH) {
        this.incomeAmountUSD = incomeAmountUSD;
        this.incomeAmountUAH = incomeAmountUAH;
    }

    public void setExpenseUSD(Integer expenseAmountUSD, Integer expenseAmountUAH) {
        this.expenseAmountUSD = expenseAmountUSD;
        this.expenseAmountUAH = expenseAmountUAH;
    }

    public Double getIncomeAmountUSD() {
        return getMoneyValue(incomeAmountUSD);
    }

    public Double getIncomeAmountUAH() {
        return getMoneyValue(incomeAmountUAH);
    }

    public Double getExpenseAmountUSD() {
        return getMoneyValue(expenseAmountUSD);
    }

    public Double getExpenseAmountUAH() {
        return getMoneyValue(expenseAmountUAH);
    }

    public void setIncomeUAH(String transPurpose, Integer income) {
        originalPurpose = transPurpose;
        purpose = PaymentPurposeUtil.simplifyIncomePaymentPurpose(transPurpose);
        this.income = income;
        commission = PaymentPurposeUtil.getIncomeCommissionUSD(transPurpose);
    }

    public void setExpenseUAH(String transPurpose, Integer expense) {
        originalPurpose = transPurpose;
        purpose = PaymentPurposeUtil.simplifyExpensePaymentPurpose(transPurpose);
        this.expense = expense;
        commission = 0;
    }

    public String getPurpose() {
        return purpose == null ? "" : purpose;
    }

    public String getOriginalPurpose() {
        return originalPurpose == null ? "" : originalPurpose;
    }

    public Double getIncome() {
        return getMoneyValue(income);
    }

    public Double getExpense() {
        return getMoneyValue(expense);
    }

    public Double getCommission() {
        return getMoneyValue(commission);
    }

    private Double getMoneyValue(Integer moneyValue) {
        return moneyValue == null ? null : (double) moneyValue / 100;
    }

}
