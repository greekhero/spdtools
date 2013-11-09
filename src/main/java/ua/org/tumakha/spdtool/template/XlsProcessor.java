package ua.org.tumakha.spdtool.template;

import net.sf.jxls.processor.RowProcessor;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import ua.org.tumakha.spdtool.entity.Report;
import ua.org.tumakha.spdtool.template.xls.row.BankDataRowProcessor;

import java.io.*;
import java.util.Map;

/**
 * @author Yuriy Tumakha
 */
public class XlsProcessor {

	private static final Log log = LogFactory.getLog(XlsProcessor.class);
	private static String TEMPLATES_DIRECTORY;
	public static String REPORTS_DIRECTORY;

    public static void init(String templatesBaseDir, String reportsBaseDir) {
        TEMPLATES_DIRECTORY = templatesBaseDir + "xls";
        REPORTS_DIRECTORY = reportsBaseDir + "xls";
    }

    public static void setReportsDirectory(String reportsDirectory) {
        REPORTS_DIRECTORY = reportsDirectory;
    }

    public void generateReport(Report report, Map<String, Object> beans, OutputStream os) throws InvalidFormatException, IOException {
        String srcFilePath = TEMPLATES_DIRECTORY + "/" + report.getTemplate();
        XLSTransformer transformer = new XLSTransformer();
        InputStream is = new BufferedInputStream(new FileInputStream(srcFilePath));
        Workbook workbook = transformer.transformXLS(is, beans);
        workbook.write(os);
        is.close();
        os.flush();
        os.close();
    }

	public String saveReport(XlsTemplate template, String outputFilenamePrefix, Map<String, Object> beans)
			throws InvalidFormatException, IOException {
		String outputFilename = outputFilenamePrefix + template.getOutputFilenameSuffix();
		File outputFile = new File(REPORTS_DIRECTORY + outputFilename);
		if (outputFile.getParentFile().mkdirs()) {
			log.debug("Created directory: " + outputFile.getParentFile());
		}

        XLSTransformer transformer = new XLSTransformer();
        RowProcessor rowProcessor = template.getRowProcessor();
        if (rowProcessor != null) {
            if (rowProcessor instanceof BankDataRowProcessor) {
                ((BankDataRowProcessor)rowProcessor).init((Map<Integer, Object>) beans.get("metaData"));
            }
            transformer.registerRowProcessor(rowProcessor);
        }
		transformer.transformXLS(TEMPLATES_DIRECTORY + "/" + template.getFilename(), beans, outputFile.getAbsolutePath());
		//log.debug("Saved XLS output to: " + outputFile.getAbsolutePath());
		return outputFilename;
	}

	public void cleanBaseDirectory(XlsTemplate template) throws IOException {
		cleanBaseDirectory(template, null, null);
	}

	public void cleanBaseDirectory(XlsTemplate template, Integer year, Integer quarterOrMonth) throws IOException {
		String outputpath = REPORTS_DIRECTORY + String.format(template.getTemplDirectoryFormat(), year, quarterOrMonth);
		File outputDirectory = new File(outputpath);
		// prevent delete not reports directories
		String path = outputDirectory.getAbsolutePath();
		if (path.contains("xls") && path.length() > 16) {
			FileUtils.deleteDirectory(outputDirectory);
		}
	}

}
