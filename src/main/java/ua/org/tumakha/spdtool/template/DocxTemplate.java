package ua.org.tumakha.spdtool.template;

/**
 * @author Yuriy Tumakha
 */
public enum DocxTemplate {
	
	ACT("Act_PE.docx", true),
	CONTRACT_ANNEX("Contract_PE_Annex.docx", true),
	CONTRACT("Contract_PE.docx", true),
	CONTRACT_ADITIONAL_AGREEMENT("Contract_PE_Additional_Agreement.docx", true),
	TAX_SYSTEM_STATEMENT("Tax_System_Statement.docx", false),
	INCOME_CALCULATION("Income_Calculation.docx", false),
	FORM_20_OPP("20-OPP.docx", false),
	FORM_11_KVED("Form11_Kved.docx", true);

	private String filename;
	private boolean freemarker;

	private DocxTemplate(String filename, boolean freemarker) {
		this.filename = filename;
		this.freemarker = freemarker;
	}

	public String getFilename() {
		return filename;
	}

	public boolean isFreemarker() {
		return freemarker;
	}

}
