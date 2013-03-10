package ua.org.tumakha.spdtool.template;

/**
 * @author Yuriy Tumakha
 */
public enum TextTemplate {

	PAYMENT_DETAILS_EMAIL("PaymentDetails.email.txt");

	private String filename;

	private TextTemplate(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
