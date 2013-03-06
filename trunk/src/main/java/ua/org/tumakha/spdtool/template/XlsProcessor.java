package ua.org.tumakha.spdtool.template;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * @author Yuriy Tumakha
 */
public class XlsProcessor {

	private static final Log log = LogFactory.getLog(XlsProcessor.class);
	private static final String TEMPLATES_DIRECTORY = "C:/spdtool-data/templates/xls";
	public static final String REPORTS_DIRECTORY = "C:/Reports/xls";
	private final XLSTransformer transformer = new XLSTransformer();

	public String saveReport(XlsTemplate template, String outputFilenamePrefix, Map<String, Object> beans)
			throws InvalidFormatException, IOException {
		String outputFilename = outputFilenamePrefix + template.getFilename();
		File outputFile = new File(REPORTS_DIRECTORY + outputFilename);
		if (outputFile.getParentFile().mkdirs()) {
			log.debug("Created directory: " + outputFile.getParentFile());
		}

		transformer.transformXLS(TEMPLATES_DIRECTORY + "/" + template.getFilename(), beans,
				outputFile.getAbsolutePath());
		log.debug("Saved XLS output to: " + outputFilename);
		return outputFilename;
	}

	public void cleanBaseDirectory(XlsTemplate template, Integer year, Integer quarter) throws IOException {
		String outputpath = REPORTS_DIRECTORY + String.format("/DECLARATION/%d_Q%d", year, quarter);
		File outputBaseDirectory = new File(outputpath);
		// prevent delete not reports directories
		String path = outputBaseDirectory.getAbsolutePath();
		if (path.contains("xls") && path.length() > 16) {
			FileUtils.deleteDirectory(outputBaseDirectory);
		}
	}

}
