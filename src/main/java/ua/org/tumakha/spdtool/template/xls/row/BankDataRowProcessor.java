package ua.org.tumakha.spdtool.template.xls.row;

import net.sf.jxls.processor.RowProcessor;
import net.sf.jxls.transformer.Row;
import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yuriy Tumakha
 */
public class BankDataRowProcessor implements RowProcessor {

    public static final String BANK_STATEMENT_SHEET_NAME = "Виписка";
    private static final String FIRST_TRANSACTION_CELL_VALUE = "${trans.dateUSD}";
    private static final String USD_BALANCE_CELL_VALUE = "${initBalanceUSD}";
    private ThreadLocal<Map<Integer, Object>> metaData = new ThreadLocal<Map<Integer, Object>>();
    private ThreadLocal<List<Integer>> totalRowNumbers = new ThreadLocal<List<Integer>>();

    public void init(Map<Integer, Object> metaDataMap) {
        metaData.set(metaDataMap);
        totalRowNumbers.set(new ArrayList<Integer>());
    }

    private OperationRowMetaData getMetaData(int rowNum) {
        return (OperationRowMetaData) metaData.get().get(rowNum);
    }

    private List<Integer> getTotalRowNumbers() {
        return totalRowNumbers.get();
    }

    @Override
    public void processRow(Row row, Map map) {
        if (!BANK_STATEMENT_SHEET_NAME.equals(row.getSheet().getSheetName())) {
            return;
        }
        org.apache.poi.ss.usermodel.Row poiRow = row.getPoiRow();
        Cell firstCell = poiRow.getCell(0);
        int rowNum = poiRow.getRowNum() + 1;
        if (firstCell != null && FIRST_TRANSACTION_CELL_VALUE.equals(firstCell.getStringCellValue())) {
            // Balance calculation
            setCellFormula(poiRow, 3, "D" + (rowNum - 1) + "+F" + rowNum + "-H" + rowNum);
            setCellFormula(poiRow, 4, "E" + (rowNum - 1) + "+G" + rowNum + "-I" + rowNum);
            setCellFormula(poiRow, 16, "Q" + (rowNum - 1) + "+R" + rowNum + "-S" + rowNum);

            OperationRowMetaData rowMetaData = getMetaData(rowNum);
            if (rowMetaData != null) {
                if (rowMetaData.isIncomeUSD()) {
                    setEquivalentInUAH(poiRow, rowNum, true);
                }
                if (rowMetaData.isExpenseUSD()) {
                    setEquivalentInUAH(poiRow, rowNum, false);
                }
                if (rowMetaData.isIncomeUAH()) {
                    setIncomeUAH(poiRow, rowNum, rowMetaData.getUsdRowNum(), rowMetaData.isCommission());
                }
            }
        } else if (firstCell != null && firstCell.getStringCellValue().startsWith("Всього")) {
            getTotalRowNumbers().add(rowNum);
        } else if (getTotalRowNumbers().size() > 1 && getTotalRowNumbers().contains(rowNum - 2)) {
            StringBuffer formula = new StringBuffer();
            for (Integer totalRowNum : getTotalRowNumbers()) {
                formula.append("G");
                formula.append(totalRowNum + 1);
                formula.append("+");
            }
            String formulaStr = formula.toString().substring(0, formula.length() - 1);
            setCellFormula(poiRow, 6, formulaStr);
        } else if (getTotalRowNumbers().size() > 0 && poiRow.getCell(3) != null && USD_BALANCE_CELL_VALUE.equals(poiRow.getCell(3).getStringCellValue())) {
            Integer lastOperationRow = getTotalRowNumbers().get(getTotalRowNumbers().size() - 1) - 1;
            setCellFormula(poiRow, 3, "D" + lastOperationRow);
            setCellFormula(poiRow, 4, "E" + lastOperationRow);
            setCellFormula(poiRow, 16, "Q" + lastOperationRow);
        }
    }

    private void setCellFormula(org.apache.poi.ss.usermodel.Row poiRow, int cellNum, String formula) {
        poiRow.getCell(cellNum).setCellFormula(formula);
    }

    private void setEquivalentInUAH(org.apache.poi.ss.usermodel.Row poiRow, int rowNum, boolean income) {
        int cellNum = income ? 6 : 8;
        String usdColumn = income ? "F" : "H";
        setCellFormula(poiRow, cellNum, "ROUND(" + usdColumn + rowNum + "*B" + rowNum + ",2)");
    }

    private void setIncomeUAH(org.apache.poi.ss.usermodel.Row poiRow, int rowNum, int usdRowNum, boolean isCommission) {
        boolean sellUSD = usdRowNum != 0;
        int cellNum = sellUSD ? 11 : 12;
        String formula = (sellUSD || isCommission ? "U" : "R") + rowNum;
        if (sellUSD) {
            formula += "-I" + usdRowNum;
        }
        setCellFormula(poiRow, cellNum, formula);
    }

}
