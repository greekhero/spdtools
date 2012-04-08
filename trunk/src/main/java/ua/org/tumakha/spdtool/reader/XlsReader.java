package ua.org.tumakha.spdtool.reader;

import static org.springframework.util.Assert.notNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReadStatus;
import net.sf.jxls.reader.XLSReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.xml.sax.SAXException;

/**
 * @author Yuriy Tumakha
 */
public class XlsReader {

	private static final Log log = LogFactory.getLog(XlsReader.class);
	private static final String MAPPINGS_DIRECTORY = "mapping/xls/";
	private Map<String, Object> beans = new HashMap<String, Object>();

	public void addBean(String name, Object value) {
		beans.put(name, value);
	}

	public Object getBean(String name) {
		return beans.get(name);
	}

	public void read(InputStream inputXLS, XlsMapping xlsMapping)
			throws SAXException, IOException, InvalidFormatException {
		notNull(inputXLS);
		InputStream inputXMLConfig = new BufferedInputStream(Thread
				.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(
						MAPPINGS_DIRECTORY + xlsMapping.getFilename()));

		XLSReader xlsReader = ReaderBuilder.buildFromXML(inputXMLConfig);
		XLSReadStatus readStatus = xlsReader.read(inputXLS, beans);
		if (!readStatus.isStatusOK()) {
			log.error("Read XLS failed.\n" + readStatus.getReadMessages());
		}
	}

}
