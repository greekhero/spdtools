package ua.org.tumakha.spdtool.template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Yuriy Tumakha
 */
public class FreeMarkerProccessor {

	private Configuration cfg;

	public FreeMarkerProccessor(String templatesDirectory) throws IOException {
		cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(templatesDirectory));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}

	public String processTemplate(String templateName,
			Map<String, String> rootMap) throws IOException, TemplateException {
		/* Get or create a template */
		Template temp = cfg.getTemplate(templateName);

		/* Merge data-model with template */
		StringWriter writer = new StringWriter();
		temp.process(rootMap, writer);
		writer.flush();
		return writer.toString();
	}

	public static FreeMarkerProccessor getInstance(String templatesDirectory)
			throws IOException {
		return new FreeMarkerProccessor(templatesDirectory);
	}

}