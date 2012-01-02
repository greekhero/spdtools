package ua.org.tumakha.spd.enums;

/**
 * @author Yuriy Tumakha
 */
public enum RegDocumentType {

	SVIDOCTVO("Свідоцтво про державну реєстрацію"),
	VYPYSKA("Виписка з єдиного державного реєстру юридичних осіб та фізичних осіб-підприємців");

	private String description;

	private RegDocumentType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
