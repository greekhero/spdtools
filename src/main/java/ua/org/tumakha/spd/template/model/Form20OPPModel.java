package ua.org.tumakha.spd.template.model;

import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.template.DocxTemplate.Template;

/**
 * @author Yuriy Tumakha
 */
public class Form20OPPModel extends TemplateModel {

	private String firstname;

	public Form20OPPModel() {
	}

	public Form20OPPModel(User user) {
		copyProperties(user);
	}

	private void copyProperties(User user) {
	}

	@Override
	public String getOutputFilename(Template template) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public String getOutputFilename(Template template) {
	// String month = "";
	// try {
	// month = yearMonthFormat.format(actPeriodFormatEn.parse(dateToEn));
	// } catch (ParseException e) {
	// throw new IllegalStateException(e);
	// }
	// return String.format("/%s/ICGU_%s_%s_%s_%s", month,
	// firstnameEn.substring(0, 1) + middlenameEn.charAt(0)
	// + lastnameEn.charAt(0), month.replace("-", "_"),
	// lastnameEn, template.getFilename());
	//
	// }

}
