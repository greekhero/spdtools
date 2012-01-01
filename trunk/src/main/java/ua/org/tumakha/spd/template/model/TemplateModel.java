package ua.org.tumakha.spd.template.model;

import java.util.Locale;

import ua.org.tumakha.spd.template.Template;

/**
 * @author Yuriy Tumakha
 */
public abstract class TemplateModel {

	protected static final Locale uaLocale = new Locale("uk", "UA");
	protected static final Locale enLocale = Locale.ENGLISH;

	public abstract String getOutputFilename(Template template);

}
