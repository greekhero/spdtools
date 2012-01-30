package ua.org.tumakha.spdtool.template;

/**
 * @author Yuriy Tumakha
 */
public enum DocxTemplate {
	
	ACT("Act_PE.docx"),
	CONTRACT_AMENDMENT("Contract_PE_Amendment.docx"),
	CONTRACT("Contract_PE.docx"),
	TAX_SYSTEM_STATEMENT("Tax_System_Statement.docx"),
	INCOME_CALCULATION("Income_Calculation.docx"),
	FORM_20_OPP("20-OPP.docx");

	private String filename;

	private DocxTemplate(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
