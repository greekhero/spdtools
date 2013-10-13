package ua.org.tumakha.spdtool.enums;

/**
 * @author Yuriy Tumakha
 */
public enum RentType {

	NONE(0, ""),
	OFFICE(642, "офісного приміщення"),
	OFFICE_EQUIPMENT(1350, "офісного приміщення та обладнання"),
	EQUIPMENT(708, "офісного обладнання");

	private int amount;

	private String description;

	private RentType(int amount, String description) {
		this.amount = amount;
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

    public String getLabel() {
        return null;
    }

}