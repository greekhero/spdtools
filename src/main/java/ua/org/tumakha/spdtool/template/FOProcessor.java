package ua.org.tumakha.spdtool.template;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.commons.beanutils.BeanMap;
import org.apache.fop.apps.FOPException;
import org.apache.log4j.Logger;

import ua.org.tumakha.spdtool.template.model.TemplateModel;
import freemarker.template.TemplateException;

/**
 * @author Yuriy Tumakha
 */
public class FOProcessor {

	private static final Logger log = Logger.getLogger(FOProcessor.class);
	private static final String TEMPLATES_DIRECTORY = "C:/spdtool-data/templates/fo";
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

	public List<String> savePdf(FOTemplate template,
			List<? extends TemplateModel> listModel)
			throws TransformerException, FOPException, TemplateException,
			IOException {
		List<String> fileNames = new ArrayList<String>();
		if (listModel == null || listModel.size() == 0) {
			log.debug("listModel is empty");
		} else {
			for (TemplateModel model : listModel) {
				fileNames.add(saveDocument(template, model));
			}
		}
		return fileNames;
	}

	private String saveDocument(FOTemplate template, TemplateModel model)
			throws TransformerException, FOPException, TemplateException,
			IOException {
		// process as FreeMarker template
		String foXml = FREE_MARKER_PROCCESSOR.processTemplate(
				template.getFilename(), getMappings(model));

		FOTemplateRenderer foTemplateRenderer = new FOTemplateRenderer(
				new StringReader(foXml));

		String outputfilepath = REPORTS_DIRECTORY
				+ model.getOutputFilename(template).replace(".fo", ".pdf");
		File outputFile = new File(outputfilepath);
		if (outputFile.getParentFile().mkdirs()) {
			log.debug("Created directory: " + outputFile.getParentFile());
		}
		foTemplateRenderer.savePdf(outputFile);
		log.debug("Saved output to: " + outputfilepath);
		return outputfilepath;
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
		// log.debug(mappings);
		return mappings;
	}

}
