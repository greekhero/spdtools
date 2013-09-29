package ua.org.tumakha.spdtool.template.model;

import org.apache.poi.ss.usermodel.Row;
import ua.org.tumakha.spdtool.template.xls.row.BankDataRowProcessor;

import java.util.Date;

/**
 * @author Yuriy Tumakha
 */
public class SpdBookDay {

    private Date date;
    private boolean first;
    private boolean firstInQuarter;
    private boolean firstInMonth;
    private StringBuffer formula = new StringBuffer();

    public SpdBookDay(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isFirstInQuarter() {
        return firstInQuarter;
    }

    public void setFirstInQuarter(boolean firstInQuarter) {
        this.firstInQuarter = firstInQuarter;
    }

    public boolean isFirstInMonth() {
        return firstInMonth;
    }

    public void setFirstInMonth(boolean firstInMonth) {
        this.firstInMonth = firstInMonth;
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

    private String getFormula() {
        return formula.toString();
    }

    private String getTotalFormula(int poiRow) {
        int lastDayRow = poiRow;
        if (!isFirst()) {
            if (isFirstInQuarter()) {
                lastDayRow--;
            }
            if (isFirstInMonth()) {
                lastDayRow--;
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("J");
        sb.append(lastDayRow);
        sb.append("+B");
        sb.append(poiRow + 1);
        return sb.toString();
    }

    public void processRow(Row poiRow) {
        poiRow.getCell(1).setCellFormula(getFormula());
        poiRow.getCell(9).setCellFormula(getTotalFormula(poiRow.getRowNum()));
    }

}
