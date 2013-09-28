package ua.org.tumakha.spdtool.template.xls.row;

import java.util.Date;

/**
 * @author Yuriy Tumakha
 */
public class OperationRowMetaData {

    private Date date;

    private boolean incomeUSD;

    private boolean expenseUSD;

    private boolean incomeUAH;

    private int usdRowNum;

    private boolean commission;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFinRes() {
        return usdRowNum != 0;
    }

    public boolean isOtherIncome() {
        return isIncomeUAH() && !isFinRes();
    }

    public boolean isIncomeUSD() {
        return incomeUSD;
    }

    public void setIncomeUSD(boolean incomeUSD) {
        this.incomeUSD = incomeUSD;
    }

    public boolean isExpenseUSD() {
        return expenseUSD;
    }

    public void setExpenseUSD(boolean expenseUSD) {
        this.expenseUSD = expenseUSD;
    }

    public boolean isIncomeUAH() {
        return incomeUAH;
    }

    public void setIncomeUAH(boolean incomeUAH) {
        this.incomeUAH = incomeUAH;
    }

    public int getUsdRowNum() {
        return usdRowNum;
    }

    public void setUsdRowNum(int usdRowNum) {
        this.usdRowNum = usdRowNum;
    }

    public boolean isCommission() {
        return commission;
    }

    public void setCommission(boolean commission) {
        this.commission = commission;
    }

}
