package ua.org.tumakha.spdtool.template;

import freemarker.template.TemplateException;
import org.apache.fop.apps.FOPException;
import org.apache.log4j.Logger;
import ua.org.tumakha.spdtool.template.model.TemplateModel;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author Yuriy Tumakha
 */
public class FOProcessor extends TextProcessor {

	private static final Logger log = Logger.getLogger(FOProcessor.class);
	private static String TEMPLATES_DIRECTORY;
	private static String REPORTS_DIRECTORY;
	private static FreeMarkerProccessor FREE_MARKER_PROCCESSOR;

    public FOProcessor(ExecutorService executorService) {
        setExecutorService(executorService);
    }

    public static void init(String templatesBaseDir, String reportsBaseDir) {
        TEMPLATES_DIRECTORY = templatesBaseDir + "fo";
        REPORTS_DIRECTORY = reportsBaseDir + "docx";
        FREE_MARKER_PROCCESSOR = getFreeMarkerProccessor(TEMPLATES_DIRECTORY);
    }

    public static void setReportsDirectory(String reportsDirectory) {
        REPORTS_DIRECTORY = reportsDirectory;
    }

    public List<String> saveReports(FOTemplate template, List<? extends TemplateModel> listModel, FOType... types)
            throws TransformerException, FOPException, TemplateException, IOException, ExecutionException, InterruptedException {
		List<String> fileNames = new ArrayList<String>();
		if (listModel == null || listModel.size() == 0) {
			log.debug("listModel is empty");
		} else {
            Set<Future<Set<String>>> results = new HashSet<Future<Set<String>>>();
            for (TemplateModel model : listModel) {
                results.add(getExecutorService().submit(new SaveDocumentCallable(template, model, types)));
            }
            for (Future<Set<String>> result : results) {
                fileNames.addAll(result.get());
            }
		}
		return fileNames;
	}

    private static class SaveDocumentCallable implements Callable {

        private FOTemplate template;
        private TemplateModel model;
        private FOType[] types;

        public SaveDocumentCallable(FOTemplate template, TemplateModel model, FOType... types) {
            this.template = template;
            this.model = model;
            this.types = types;
        }

        public Set<String> call() throws TransformerException, FOPException, TemplateException, IOException {
            // process as FreeMarker template
            String foXml = FREE_MARKER_PROCCESSOR.processTemplate(template.getFilename(), getMappings(model));

            FOTemplateRenderer foTemplateRenderer = new FOTemplateRenderer(foXml);

            Set<String> filenames = new HashSet<String>(types.length);
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
                filenames.add(outputfilepath);
                //log.debug("Saved output to: " + outputFile.getAbsolutePath());
            }
            return filenames;
        }

    }

}
