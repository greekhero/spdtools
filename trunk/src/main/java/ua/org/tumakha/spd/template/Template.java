package ua.org.tumakha.spd.template;

/**
 * @author Yuriy Tumakha
 */
public enum Template {
	ACT("Act_PE.docx"),
	CONTRACT_AMENDMENT("Contract_PE_Amendment.docx"),
	TAX_SYSTEM_STATEMENT("Tax_System_Statement.docx"),
	INCOME_CALCULATION("Income_Calculation.docx"),
	FORM_20_OPP("20-OPP.docx");

	private String filename;

	private Template(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
