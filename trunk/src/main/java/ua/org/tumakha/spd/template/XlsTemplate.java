package ua.org.tumakha.spd.template;

/**
 * @author Yuriy Tumakha
 */
public enum XlsTemplate {

	ESV_D5("ESV_d5.xls");

	private String filename;

	private XlsTemplate(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
