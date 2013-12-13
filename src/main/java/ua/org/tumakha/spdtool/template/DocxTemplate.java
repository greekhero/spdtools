package ua.org.tumakha.spdtool.template;

/**
 * @author Yuriy Tumakha
 */
public enum DocxTemplate {
	
	ACT("Act_PE.docx"),
	CONTRACT_ANNEX("Contract_PE_Annex.docx"),
	CONTRACT("Contract_PE.docx"),
	CONTRACT_ADITIONAL_AGREEMENT("Contract_PE_Additional_Agreement.docx"),
	TAX_SYSTEM_STATEMENT("Tax_System_Statement.docx"),
	INCOME_CALCULATION("Income_Calculation.docx"),
	FORM_20_OPP("20-OPP.docx"),
	FORM_11_KVED("Form11_Kved.docx"),
	ECP_REGISTRATION("ECP_Registration.docx"),
	ECP_JOIN("ECP_Join.docx");

	private String filename;

	private DocxTemplate(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
