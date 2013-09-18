package ua.org.tumakha.spdtool.template;

/**
 * @author Yuriy Tumakha
 */
public enum XlsTemplate {

    BANK_DATA("BankData.xlsx", "/BankData", false),
	ECP_REGISTRATION("ECP_Registration.xlsx", "/ECP"),
	PAYMENTS("Payments.xls","/Payments/%d_%02d"),
	ESV_D5("ESV_d5.xls", "ESV_d5"),
	DECLARATION("Declaration.xls", "/DECLARATION/%d_Q%d");

	private String filename;
    private String templDirectoryFormat;
    private boolean includeFilename;

    private XlsTemplate(String filename, String templDirectoryFormat) {
        this(filename, templDirectoryFormat, true);
    }

    private XlsTemplate(String filename, String templDirectoryFormat, boolean includeFilename) {
        this.filename = filename;
        this.templDirectoryFormat = templDirectoryFormat;
        this.includeFilename = includeFilename;
    }

    public String getFilename() {
        return filename;
    }

    public String getOutputFilenameSuffix() {
        return includeFilename ? filename : filename.substring(filename.lastIndexOf("."));
    }

    public String getTemplDirectoryFormat() {
        return templDirectoryFormat;
    }
}
