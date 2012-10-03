package ua.org.tumakha.spdtool.template;

/**
 * @author Yuriy Tumakha
 */
public enum FOTemplate {

	ACT("Act_PE.fo"),
	CONTRACT_ANNEX("Contract_PE_Annex.fo"),
	CONTRACT("Contract_PE.fo");

	private String filename;

	private FOTemplate(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
