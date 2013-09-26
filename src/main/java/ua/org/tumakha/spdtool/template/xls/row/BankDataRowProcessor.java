package ua.org.tumakha.spdtool.template.xls.row;

import net.sf.jxls.processor.RowProcessor;
import net.sf.jxls.transformer.Row;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Map;

/**
 * @author Yuriy Tumakha
 */
public class BankDataRowProcessor implements RowProcessor {

    private static final String FIRST_TRANSACTION_CELL_VALUE = "${trans.dateUSD}";
    private ThreadLocal<Map<Integer, Object>> metaData = new ThreadLocal<Map<Integer, Object>>();

    public void init(Map<Integer, Object> metaDataMap) {
        metaData.set(metaDataMap);
    }

    private OperationRowMetaData getMetaData(int rowNum) {
        return (OperationRowMetaData) metaData.get().get(rowNum);
    }

    @Override
    public void processRow(Row row, Map map) {
        org.apache.poi.ss.usermodel.Row poiRow = row.getPoiRow();
        Cell firstCell = poiRow.getCell(0);
        if (firstCell != null && FIRST_TRANSACTION_CELL_VALUE.equals(firstCell.getStringCellValue())) {
            int rowNum = poiRow.getRowNum() + 1;
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
