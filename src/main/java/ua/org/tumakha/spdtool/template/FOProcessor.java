package ua.org.tumakha.spdtool.template;

import freemarker.template.TemplateException;
import org.apache.fop.apps.FOPException;
import org.apache.log4j.Logger;
import ua.org.tumakha.spdtool.template.model.TemplateModel;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuriy Tumakha
 */
public class FOProcessor extends TextProcessor implements BaseConfig {

	private static final Logger log = Logger.getLogger(FOProcessor.class);
	private static final String TEMPLATES_DIRECTORY = TEMPLATES_BASE + "fo";
	private static final String REPORTS_DIRECTORY = REPORTS_BASE + "docx";
	private final FreeMarkerProccessor FREE_MARKER_PROCCESSOR = getFreeMarkerProccessor(TEMPLATES_DIRECTORY);

	public List<String> saveReports(FOTemplate template, List<? extends TemplateModel> listModel, FOType... types)
			throws TransformerException, FOPException, TemplateException, IOException {
		List<String> fileNames = new ArrayList<String>();
		if (listModel == null || listModel.size() == 0) {
			log.debug("listModel is empty");
		} else {
			for (TemplateModel model : listModel) {
				saveDocument(fileNames, template, model, types);
			}
		}
		return fileNames;
	}

	private void saveDocument(List<String> fileNames, FOTemplate template, TemplateModel model, FOType... types)
			throws TransformerException, FOPException, TemplateException, IOException {
		// process as FreeMarker template
		String foXml = FREE_MARKER_PROCCESSOR.processTemplate(template.getFilename(), getMappings(model));

		FOTemplateRenderer foTemplateRenderer = new FOTemplateRenderer(foXml);

		for (FOType type : types) {
			String tempateFilename = model.getOutputFilename(template);
			String outputFilename = null;
			if (type == FOType.PDF) {
				outputFilename = tempateFilename.replace(".fo", ".pdf");
			} else if (type == FOType.RTF) {
				outputFilename = tempateFilename.replace(".fo", ".rtf");
			}
			String outputfilepath = REPORTS_DIRECTORY + outputFilename;

			File outputFile = new File(outputfilepath);
			if (outputFile.getParentFile().mkdirs()) {
				log.debug("Created directory: " + outputFile.getParentFile());
			}
			if (type == FOType.PDF) {
				foTemplateRenderer.savePdf(outputFile);
			} else if (type == FOType.RTF) {
				foTemplateRenderer.saveRtf(outputFile);
			}
			fileNames.add(outputfilepath);
			log.debug("Saved output to: " + outputfilepath);
		}
	}

}
