package ua.org.tumakha.spdtool.template;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanMap;
import org.apache.log4j.Logger;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;

import ua.org.tumakha.spdtool.template.model.TemplateModel;
import freemarker.template.TemplateException;

/**
 * @author Yuriy Tumakha
 */
public class DocxProcessor {

	public static JAXBContext context = org.docx4j.jaxb.Context.jc;
	private static final Logger log = Logger.getLogger(DocxProcessor.class);
	private static final String TEMPLATES_DIRECTORY = "C:/spdtool-data/templates/docx";
	private static final String REPORTS_DIRECTORY = "C:/Reports/docx";
	private static final FreeMarkerProccessor FREE_MARKER_PROCCESSOR = getFreeMarkerProccessor();

	private static FreeMarkerProccessor getFreeMarkerProccessor() {
		try {
			return FreeMarkerProccessor.getInstance(TEMPLATES_DIRECTORY);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public void saveReport(TemplateModel model) {
		log.debug(model.getClass());// TODO:
	}

	public void saveReports(DocxTemplate template,
			List<? extends TemplateModel> listModel) throws JAXBException,
			Docx4JException, TemplateException, IOException {

		if (listModel == null || listModel.size() == 0) {
			log.debug("listModel is empty");
			return;
		}
		// Open a document from the file system
		// 1. Load the Package
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File(TEMPLATES_DIRECTORY + "/"
						+ template.getFilename()));

		// 2. Fetch the document part
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

		org.docx4j.wml.Document wmlDocumentEl = documentPart.getJaxbElement();

		// xml --> string
		String xml = XmlUtils.marshaltoString(wmlDocumentEl, true);

		for (TemplateModel model : listModel) {
			saveDocument(wordMLPackage, xml, model, template);
		}

	}

	private static void saveDocument(WordprocessingMLPackage wordMLPackage,
			String xml, TemplateModel model, DocxTemplate template)
			throws JAXBException, Docx4JException, TemplateException,
			IOException {
		String outputfilepath = REPORTS_DIRECTORY
				+ model.getOutputFilename(template);
		Object obj = null;
		if (template.isFreemarker()) {
			// process as FreeMarker template
			Map<String, ?> mappings = getMappings(model);
			xml = FREE_MARKER_PROCCESSOR.processTemplate(template.getFilename()
					.replace(".docx", ".xml"), mappings);
			obj = XmlUtils.unmarshalString(xml);
		} else {
			// simple replace mappings
			HashMap<String, String> mappings = getStringMappings(model);
			obj = XmlUtils.unmarshallFromTemplate(xml, mappings);
		}

		// change JaxbElement
		wordMLPackage.getMainDocumentPart().setJaxbElement((Document) obj);

		File outputFile = new File(outputfilepath);
		if (outputFile.getParentFile().mkdirs()) {
			log.debug("Created directory: " + outputFile.getParentFile());
		}
		SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
		saver.save(outputfilepath);
		log.debug("Saved output to: " + outputfilepath);
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, String> getStringMappings(TemplateModel model) {
		HashMap<String, String> mappings = new HashMap<String, String>();
		BeanMap beanMap = new BeanMap(model);
		for (Object o : beanMap.entrySet()) {
			Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) o;
			String value = "";
			if (entry.getValue() != null) {
				value = entry.getValue().toString();
			}
			mappings.put(entry.getKey().toString(), value);
		}
		log.debug(mappings);
		return mappings;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, ?> getMappings(TemplateModel model) {
		HashMap<String, Object> mappings = new HashMap<String, Object>();
		BeanMap beanMap = new BeanMap(model);
		for (Object o : beanMap.entrySet()) {
			Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) o;
			Object value = "";
			if (entry.getValue() != null) {
				value = entry.getValue();
			}
			mappings.put(entry.getKey().toString(), value);
		}
		log.debug(mappings);
		return mappings;
	}

}
