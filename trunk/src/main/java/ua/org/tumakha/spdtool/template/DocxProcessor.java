package ua.org.tumakha.spdtool.template;

import freemarker.template.TemplateException;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.docx4j.XmlUtils;
import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.Conversion;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;
import ua.org.tumakha.spdtool.template.model.TemplateModel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author Yuriy Tumakha
 */
public class DocxProcessor extends TextProcessor {

    public static JAXBContext context = org.docx4j.jaxb.Context.jc;
	private static final Logger log = Logger.getLogger(DocxProcessor.class);
	private static String TEMPLATES_DIRECTORY;
	private static String REPORTS_DIRECTORY;
	private static FreeMarkerProccessor FREE_MARKER_PROCCESSOR;

    public DocxProcessor(ExecutorService executorService) {
        setExecutorService(executorService);
    }

    public static void init(String templatesBaseDir, String reportsBaseDir) {
        TEMPLATES_DIRECTORY = templatesBaseDir + "docx";
        REPORTS_DIRECTORY = reportsBaseDir + "docx";
        FREE_MARKER_PROCCESSOR = getFreeMarkerProccessor(TEMPLATES_DIRECTORY);
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
            throws JAXBException, Docx4JException, TemplateException, IOException, ExecutionException, InterruptedException {

		List<String> fileNames = new ArrayList<String>();

		if (listModel == null || listModel.size() == 0) {
			log.debug("listModel is empty");
			return fileNames;
		}
		// Open a document from the file system
		// 1. Load the Package
        File templateFile = new File(TEMPLATES_DIRECTORY + "/" + template.getFilename());
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateFile);

		// 2. Fetch the document part
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

		org.docx4j.wml.Document wmlDocumentEl = documentPart.getJaxbElement();

		// xml --> string
		String xml = XmlUtils.marshaltoString(wmlDocumentEl, true);

        // Create directory
        String outputfilepath = REPORTS_DIRECTORY + listModel.get(0).getOutputFilename(template);
        File outputDirectory = new File(outputfilepath).getParentFile();
        if (outputDirectory.mkdirs()) {
            log.debug("Created directory: " + outputDirectory);
        }

        Set<Future<String>> results = new HashSet<Future<String>>();
        for (TemplateModel model : listModel) {
            results.add(getExecutorService().submit(new SaveDocumentCallable(wordMLPackage, model, template, xml)));
        }
        for (Future<String> result : results) {
            fileNames.add(result.get());
        }

		return fileNames;
	}

	private static void savePdf(WordprocessingMLPackage wordMLPackage, String outputfilepath)
			throws FileNotFoundException, Docx4JException {
		// the PdfConversion object
		PdfConversion c = new Conversion(wordMLPackage);

		// for demo/debugging purposes, save the intermediate XSL FO
		((org.docx4j.convert.out.pdf.viaXSLFO.Conversion) c).setSaveFO(new java.io.File(outputfilepath.replace(".docx",
				".fo.xml")));

		// PdfConversion writes to an output stream
		String pdffilepath = outputfilepath.replace(".docx", ".pdf");
		OutputStream os = new java.io.FileOutputStream(pdffilepath);
		c.output(os, new PdfSettings());
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
		// log.debug(mappings);
		return mappings;
	}

    private static class SaveDocumentCallable implements Callable {

        private WordprocessingMLPackage wordMLPackage;
        private TemplateModel model;
        private DocxTemplate template;
        private String xml;

        public SaveDocumentCallable(WordprocessingMLPackage wordMLPackage, TemplateModel model, DocxTemplate template, String xml) {
            this.wordMLPackage = wordMLPackage;
            this.model = model;
            this.template = template;
            this.xml = xml;
        }

        public String call() throws IOException, TemplateException, JAXBException, Docx4JException {
            String outputfilepath = REPORTS_DIRECTORY + model.getOutputFilename(template);
            Object obj = null;
            if (template.isFreemarker()) {
                // process as FreeMarker template
                Map<String, ?> mappings = getMappings(model);
                xml = FREE_MARKER_PROCCESSOR.processTemplate(template.getFilename().replace(".docx", ".xml"), mappings);
                obj = XmlUtils.unmarshalString(xml);
            } else {
                // simple replace mappings
                HashMap<String, String> mappings = getStringMappings(model);
                obj = XmlUtils.unmarshallFromTemplate(xml, mappings);
            }

            File outputFile = new File(outputfilepath);
            File outputDirectory = outputFile.getParentFile();
            if (outputDirectory.mkdirs()) {
                log.debug("Created directory: " + outputDirectory);
            }

            synchronized (log) {
                // change JaxbElement
                wordMLPackage.getMainDocumentPart().setJaxbElement((Document) obj);

                SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
                saver.save(outputfilepath);
                //log.debug("Saved output to: " + outputFile.getAbsolutePath());
            }

            // savePdf(wordMLPackage, outputfilepath);

            return outputfilepath;
        }

    }

}
