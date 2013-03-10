package ua.org.tumakha.spdtool.template;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.log4j.Logger;

import ua.org.tumakha.spdtool.template.model.TemplateModel;
import freemarker.template.TemplateException;

/**
 * @author Yuriy Tumakha
 */
public class TextProcessor {

	private static final Logger log = Logger.getLogger(TextProcessor.class);
	private static final String TEMPLATES_DIRECTORY = "C:/spdtool-data/templates/text";
	private final FreeMarkerProccessor FREE_MARKER_PROCCESSOR = getFreeMarkerProccessor(TEMPLATES_DIRECTORY);

	protected static FreeMarkerProccessor getFreeMarkerProccessor(String templatesDirectory) {
		try {
			return FreeMarkerProccessor.getInstance(templatesDirectory);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public String processTemplateText(TextTemplate template, Map<String, Object> beans) throws TemplateException,
			IOException {
		return FREE_MARKER_PROCCESSOR.processTemplate(template.getFilename(), beans);
	}

	@SuppressWarnings("unchecked")
	protected static Map<String, ?> getMappings(TemplateModel model) {
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
