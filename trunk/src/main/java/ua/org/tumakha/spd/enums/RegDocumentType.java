package ua.org.tumakha.spd.enums;

/**
 * @author Yuriy Tumakha
 */
public enum RegDocumentType {

	SVIDOCTVO("Свідоцтво про державну реєстрацію",
			"Certificate of state registration"),
	VYPYSKA("Виписка з єдиного державного реєстру юридичних осіб та фізичних осіб-підприємців",
			"Excerpt from the Unified State Register of Legal Entities and Individual Entrepreneurs");

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

}
