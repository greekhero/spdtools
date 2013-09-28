package ua.org.tumakha.spdtool.template.model;

import ua.org.tumakha.spdtool.template.xls.row.BankDataRowProcessor;

import java.util.Date;

/**
 * @author Yuriy Tumakha
 */
public class SpdBookDay {

    private Date date;

    private StringBuffer formula = new StringBuffer();

    public SpdBookDay(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getFormula() {
        return formula.toString();
    }

    public void addCellRef(String column, int rowNum) {
        if (formula.length() > 0) {
            formula.append("+");
        }
        formula.append(BankDataRowProcessor.BANK_STATEMENT_SHEET_NAME);
        formula.append("!");
        formula.append(column);
        formula.append(rowNum);
    }

    public String getTotalFormula(int poiRow) {
        StringBuffer sb = new StringBuffer();
        sb.append("J");
        sb.append(poiRow);
        sb.append("+B");
        sb.append(poiRow + 1);
        return sb.toString();
    }

}
