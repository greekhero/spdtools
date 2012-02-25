package ua.org.tumakha.spdtool.template.model;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Kved2010;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.template.DocxTemplate;

/**
 * @author Yuriy Tumakha
 */
public class Form11KvedModel extends TemplateModel {

	private String kvedCode1;
	private String kvedCode2;
	private String kvedCode3;
	private String kvedCode4;
	private String kvedCode5;
	private String kvedName1;
	private String kvedName2;
	private String kvedName3;
	private String kvedName4;
	private String kvedName5;

	public Form11KvedModel() {
	}

	public Form11KvedModel(User user) {
		super(user);
		setPhone("+380976884343");
		copyProperties(user);
	}

	private void copyProperties(User user) {
		List<Kved2010> kveds = user.getKveds2010();
		Kved2010 kved = null;
		switch (kveds.size()) {
		case 5:
			kved = kveds.get(4);
			kvedCode5 = kved.getCode();
			kvedName5 = kved.getName();
		case 4:
			kved = kveds.get(3);
			kvedCode4 = kved.getCode();
			kvedName4 = kved.getName();
		case 3:
			kved = kveds.get(2);
			kvedCode3 = kved.getCode();
			kvedName3 = kved.getName();
		case 2:
			kved = kveds.get(1);
			kvedCode2 = kved.getCode();
			kvedName2 = kved.getName();
		case 1:
			kved = kveds.get(0);
			kvedCode1 = kved.getCode();
			kvedName1 = kved.getName();
		}
	}

	@Override
	public String getOutputFilename(DocxTemplate template) {
		return String.format("/Form11/%s_%s_%s", getLastnameEn(),
				getFirstnameEn(), template.getFilename());
	}

	public String getKvedCode1() {
		return kvedCode1;
	}

	public void setKvedCode1(String kvedCode1) {
		this.kvedCode1 = kvedCode1;
	}

	public String getKvedCode2() {
		return kvedCode2;
	}

	public void setKvedCode2(String kvedCode2) {
		this.kvedCode2 = kvedCode2;
	}

	public String getKvedCode3() {
		return kvedCode3;
	}

	public void setKvedCode3(String kvedCode3) {
		this.kvedCode3 = kvedCode3;
	}

	public String getKvedCode4() {
		return kvedCode4;
	}

	public void setKvedCode4(String kvedCode4) {
		this.kvedCode4 = kvedCode4;
	}

	public String getKvedCode5() {
		return kvedCode5;
	}

	public void setKvedCode5(String kvedCode5) {
		this.kvedCode5 = kvedCode5;
	}

	public String getKvedName1() {
		return kvedName1;
	}

	public void setKvedName1(String kvedName1) {
		this.kvedName1 = kvedName1;
	}

	public String getKvedName2() {
		return kvedName2;
	}

	public void setKvedName2(String kvedName2) {
		this.kvedName2 = kvedName2;
	}

	public String getKvedName3() {
		return kvedName3;
	}

	public void setKvedName3(String kvedName3) {
		this.kvedName3 = kvedName3;
	}

	public String getKvedName4() {
		return kvedName4;
	}

	public void setKvedName4(String kvedName4) {
		this.kvedName4 = kvedName4;
	}

	public String getKvedName5() {
		return kvedName5;
	}

	public void setKvedName5(String kvedName5) {
		this.kvedName5 = kvedName5;
	}

}
