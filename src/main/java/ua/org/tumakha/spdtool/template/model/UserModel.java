package ua.org.tumakha.spdtool.template.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.template.DocxTemplate;

/**
 * @author Yuriy Tumakha
 */
public class UserModel extends TemplateModel {

	private User user;

	private Date date;

	public UserModel() {
	}

	public UserModel(User user) {
		super(user);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		StringBuffer name = new StringBuffer();
		name.append(user.getLastname());
		name.append(" ");
		name.append(user.getFirstname());
		name.append(" ");
		name.append(user.getMiddlename());
		return StringUtils.rightPad(name.toString(), 100);
	}

	@Override
	public String getOutputFilename(DocxTemplate template) {
		return String.format("/ECP/%s/%s_%s_%s", getLastname(),
				getLastnameEn(), getFirstnameEn(), template.getFilename());

	}

}
