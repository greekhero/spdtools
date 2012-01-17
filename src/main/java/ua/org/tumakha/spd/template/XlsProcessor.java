package ua.org.tumakha.spd.template;

import java.io.IOException;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * @author Yuriy Tumakha
 */
public class XlsProcessor {

	private static final Log log = LogFactory.getLog(XlsProcessor.class);
	private static final String TEMPLATES_DIRECTORY = "C:/Reports/templates/xls";
	private static final String REPORTS_DIRECTORY = "C:/Reports/xls";
	private XLSTransformer transformer = new XLSTransformer();

	public void saveReport(XlsTemplate template, String outputFilenamePrefix,
			Map<String, Object> beans) throws InvalidFormatException,
			IOException {
		String outputFilename = outputFilenamePrefix + template.getFilename();
		transformer.transformXLS(
				TEMPLATES_DIRECTORY + "/" + template.getFilename(),
				beans,
				REPORTS_DIRECTORY + outputFilenamePrefix
						+ template.getFilename());
		log.debug("Saved XLS output to: " + outputFilename);
	}

}
