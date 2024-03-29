package ua.org.tumakha.spdtool.enums;

/**
 * @author Yuriy Tumakha
 */
public enum RegDocumentType {

	SVIDOCTVO("Свідоцтво про державну реєстрацію фізичної особи - підприємця",
			"Certificate of state registration Private Entrepreneur"), 
	VYPYSKA("Виписка з Єдиного державного реєстру юридичних осіб та фізичних осіб-підприємців",
			"Excerpt from the Unified State Register of Legal Entities and Private Entrepreneurs");

	private String description;
	private String descriptionEn;

	private RegDocumentType(String description, String descriptionEn) {
		this.description = description;
		this.descriptionEn = descriptionEn;
	}

	public String getDescription() {
		return description;
	}

	public String getDescriptionVOrudnomu() {
		return description.replace("во ", "ва ").replace("ка ", "ки ");
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}

    public String getLabel() {
        return null;
    }

}
