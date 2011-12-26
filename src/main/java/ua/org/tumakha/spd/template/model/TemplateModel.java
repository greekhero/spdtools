package ua.org.tumakha.spd.template.model;

import java.util.Date;
import java.util.HashMap;

import ua.org.tumakha.spd.template.DocxTemplate.Template;

/**
 * @author Yuriy Tumakha
 */
public abstract class TemplateModel {

	private Date templateDate;

	public Date getTemplateDate() {
		return templateDate;
	}

	public void setTemplateDate(Date date) {
		this.templateDate = date;
	}

	public HashMap<String, String> getMappings() {
		// TODO get all bean properties
		return null;
	}

	public abstract String getOutputFilename(Template template);

}
