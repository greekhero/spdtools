package ua.org.tumakha.spdtool.template;

/**
 * @author Yuriy Tumakha
 */
public enum XlsTemplate {

	PAYMENTS("Payments.xls"),
	ESV_D5("ESV_d5.xls"),
	DECLARATION("Declaration.xls");

	private String filename;

	private XlsTemplate(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
