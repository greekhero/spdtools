package ua.org.tumakha.spdtool.template.xls.row;

import net.sf.jxls.parser.Cell;
import net.sf.jxls.processor.RowProcessor;
import net.sf.jxls.transformer.Row;

import java.util.Map;

/**
 * @author Yuriy Tumakha
 */
public class BankDataRowProcessor implements RowProcessor {

    private static final String FIRST_LOOP_COLUMN_VALUE = "${trans.dateUSD}";

    @Override
    public void processRow(Row row, Map map) {
        if (FIRST_LOOP_COLUMN_VALUE.equals(((Cell) row.getCells().get(0)).getStringCellValue())) {
            org.apache.poi.ss.usermodel.Row poiRow = row.getPoiRow();
            int rowNum = poiRow.getRowNum();
            setCellFormula(poiRow, 3, "D" + rowNum + "+F" + (rowNum + 1) + "-H" + (rowNum + 1));
            setCellFormula(poiRow, 4, "E" + rowNum + "+G" + (rowNum + 1) + "-I" + (rowNum + 1));
            setCellFormula(poiRow, 16, "Q" + rowNum + "+R" + (rowNum + 1) + "-S" + (rowNum + 1));
        }
    }

    private void setCellFormula(org.apache.poi.ss.usermodel.Row poiRow, int cellNum, String formula) {
        poiRow.getCell(cellNum).setCellFormula(formula);
    }

}
