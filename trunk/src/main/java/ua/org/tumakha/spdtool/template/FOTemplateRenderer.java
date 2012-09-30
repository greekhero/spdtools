/**
 * 
 */
package ua.org.tumakha.spdtool.template;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Date;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.log4j.Logger;
import org.apache.xmlgraphics.util.MimeConstants;

/**
 * @author Yura
 * 
 */
public class FOTemplateRenderer {

	private static final Logger log = Logger
			.getLogger(FOTemplateRenderer.class);
	public static final String TEMPLATES_DIRECTORY = "C:/spdtool-data/templates/fo";

	private final FopFactory fopFactory = FopFactory.newInstance();
	private final TransformerFactory tFactory = TransformerFactory
			.newInstance();
	private final FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

	private final String foXml;

	public FOTemplateRenderer(String foXml) {
		this.foXml = foXml;
		foUserAgent.setProducer("XSL-FO");
		foUserAgent.setCreator("Yuriy Tumakha");
		foUserAgent.setAuthor("Yuriy Tumakha");
		foUserAgent.setCreationDate(new Date());
		try {
			InputStream is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("fop.xconf");
			Configuration cfg = new DefaultConfigurationBuilder().build(is);
			fopFactory.setUserConfig(cfg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void savePdf(File outputFile) throws IOException,
			TransformerException, FOPException {
		proccessTemplate(outputFile, MimeConstants.MIME_PDF);
	}

	public void saveRtf(File outputFile) throws IOException,
			TransformerException, FOPException {
		proccessTemplate(outputFile, MimeConstants.MIME_RTF_ALT2);
	}

	private void proccessTemplate(File outputFile, String mime)
			throws IOException, TransformerException, FOPException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(outputFile);
			out = new BufferedOutputStream(out);

			// Construct fop with desired output format
			Fop fop = fopFactory.newFop(mime, foUserAgent, out);

			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			Source src = new StreamSource(new StringReader(foXml));
			tFactory.newTransformer().transform(src, res);

		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} finally {
			out.close();
		}
	}

}
