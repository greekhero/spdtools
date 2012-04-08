package ua.org.tumakha.spdtool.reader;

/**
 * @author Yuriy Tumakha
 */
public enum XlsMapping {

	DECLARATION("declaration-xls-mapping.xml"),
	ACT("act-xls-mapping.xml");

	private String filename;

	private XlsMapping(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
