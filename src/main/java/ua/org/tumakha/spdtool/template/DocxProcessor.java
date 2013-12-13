package ua.org.tumakha.spdtool.template;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import ua.org.tumakha.spdtool.template.model.TemplateModel;

import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Yuriy Tumakha
 */
public class DocxProcessor extends TextProcessor {

	private static final Logger log = Logger.getLogger(DocxProcessor.class);
	private static String TEMPLATES_DIRECTORY;
	private static String REPORTS_DIRECTORY;

    public DocxProcessor(ExecutorService executorService) {
        setExecutorService(executorService);
    }

    public static void init(String templatesBaseDir, String reportsBaseDir) {
        TEMPLATES_DIRECTORY = templatesBaseDir + "docx";
        REPORTS_DIRECTORY = reportsBaseDir + "docx";
    }

    public static void setReportsDirectory(String reportsDirectory) {
        REPORTS_DIRECTORY = reportsDirectory;
    }

    public void saveReport(TemplateModel model) {
		log.debug(model.getClass());// TODO:
	}

	public void cleanBaseDirectory(DocxTemplate template, TemplateModel model) throws IOException {
		String outputfilepath = REPORTS_DIRECTORY + model.getOutputFilename(template);
		File outputFile = new File(outputfilepath);
		File outputBaseDirectory = outputFile.getParentFile().getParentFile();
		// prevent delete not reports directories
        if (outputBaseDirectory.mkdirs()) {
            log.debug("Created directory: " + outputBaseDirectory);
        } else {
            String path = outputBaseDirectory.getAbsolutePath();
            if (path.contains("docx") && path.length() > 16) {
                FileUtils.deleteDirectory(outputBaseDirectory);
            }
        }
	}

	public List<String> saveReports(DocxTemplate template, List<? extends TemplateModel> listModel)
            throws IOException, ExecutionException, InterruptedException, XDocReportException {

		List<String> fileNames = new ArrayList<String>();

		if (listModel == null || listModel.size() == 0) {
			log.debug("listModel is empty");
			return fileNames;
		}

        File templateFile = new File(TEMPLATES_DIRECTORY + "/" + template.getFilename());
        InputStream in = new FileInputStream(templateFile);
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, template.name(), TemplateEngineKind.Freemarker);

        // Create directory
        String outputfilepath = REPORTS_DIRECTORY + listModel.get(0).getOutputFilename(template);
        File outputDirectory = new File(outputfilepath).getParentFile();
        if (outputDirectory.mkdirs()) {
            log.debug("Created directory: " + outputDirectory);
        }

        Set<Future<String>> results = new HashSet<Future<String>>();
        for (TemplateModel model : listModel) {
            results.add(getExecutorService().submit(new SaveDocumentCallable(model, template)));
        }
        for (Future<String> result : results) {
            fileNames.add(result.get());
        }

		return fileNames;
	}

//    @Deprecated
//	@SuppressWarnings("unchecked")
//	private static HashMap<String, String> getStringMappings(TemplateModel model) {
//		HashMap<String, String> mappings = new HashMap<String, String>();
//		BeanMap beanMap = new BeanMap(model);
//		for (Object o : beanMap.entrySet()) {
//			Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) o;
//			String value = "";
//			if (entry.getValue() != null) {
//				value = entry.getValue().toString();
//			}
//			mappings.put(entry.getKey().toString(), value);
//		}
//		// log.debug(mappings);
//		return mappings;
//	}

    private static class SaveDocumentCallable implements Callable {

        private TemplateModel model;
        private DocxTemplate template;

        public SaveDocumentCallable(TemplateModel model, DocxTemplate template) {
            this.model = model;
            this.template = template;
        }

        public String call() throws IOException, XDocReportException {
            Map<String, Object> mappings = getMappings(model);

            String outputfilepath = REPORTS_DIRECTORY + model.getOutputFilename(template);
            File outputFile = new File(outputfilepath);
            File outputDirectory = outputFile.getParentFile();
            if (outputDirectory.mkdirs()) {
                log.debug("Created directory: " + outputDirectory);
            }

            IXDocReport report = XDocReportRegistry.getRegistry().getReport(template.name());

            synchronized (log) {
                IContext context = report.createContext();
                context.putMap(mappings);
                report.process(context, new FileOutputStream(outputFile));
                //log.debug("Saved output to: " + outputFile.getAbsolutePath());
            }

            return outputfilepath;
        }

    }

}
