package ua.org.tumakha.spd.template.model;

import ua.org.tumakha.spd.template.DocxTemplate.Template;

/**
 * @author Yuriy Tumakha
 */
public abstract class TemplateModel {

	public abstract String getOutputFilename(Template template);

}
