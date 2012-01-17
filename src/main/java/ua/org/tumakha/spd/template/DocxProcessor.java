package ua.org.tumakha.spd.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.collections.BeanMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;

import ua.org.tumakha.spd.template.model.TemplateModel;

/**
 * @author Yuriy Tumakha
 */
public class DocxProcessor {

	public static JAXBContext context = org.docx4j.jaxb.Context.jc;
	private static final Log log = LogFactory.getLog(DocxProcessor.class);
	private static final String TEMPLATES_DIRECTORY = "C:/Reports/templates";
	private static final String REPORTS_DIRECTORY = "C:/Reports";

	public void saveReport(TemplateModel model) {
		log.debug(model.getClass());// TODO:
	}

	public void saveReports(DocxTemplate template,
			List<? extends TemplateModel> listModel) throws JAXBException,
			Docx4JException {

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
			saveDocument(documentPart, wordMLPackage, xml, model, template);
		}

	}

	private static void saveDocument(MainDocumentPart documentPart,
			WordprocessingMLPackage wordMLPackage, String xml,
			TemplateModel model, DocxTemplate template) throws JAXBException,
			Docx4JException {
		HashMap<String, String> mappings = getMappings(model);
		String outputfilepath = REPORTS_DIRECTORY
				+ model.getOutputFilename(template);

		Object obj = XmlUtils.unmarshallFromTemplate(xml, mappings);

		// change JaxbElement
		documentPart.setJaxbElement((Document) obj);

		SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
		saver.save(outputfilepath);
		log.debug("Saved output to: " + outputfilepath);

	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, String> getMappings(TemplateModel model) {
		HashMap<String, String> mappings = new HashMap<String, String>();
		BeanMap beanMap = new BeanMap(model);
		for (Object o : beanMap.entrySet()) {
			Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) o;
			if (entry.getValue() == null) {
				entry.setValue("");
			}
			mappings.put(entry.getKey().toString(), entry.getValue().toString());
		}
		log.debug(mappings);
		return mappings;
	}

}
