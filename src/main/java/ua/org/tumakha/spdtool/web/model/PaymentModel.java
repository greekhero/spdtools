package ua.org.tumakha.spdtool.web.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ua.org.tumakha.spdtool.entity.User;

public class PaymentModel implements Serializable {

	private static final long serialVersionUID = 1308953328668550645L;

	private List<User> users;

	private Set<Integer> enabledUserIds;

	@Size(min = 1)
	@NotNull
	private Set<Integer> groupIds;

	private boolean sendEmail;

	public PaymentModel() {
		enabledUserIds = new HashSet<Integer>();
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Set<Integer> getEnabledUserIds() {
		return enabledUserIds;
	}

	public void setEnabledUserIds(Set<Integer> enabledUserIds) {
		this.enabledUserIds = enabledUserIds;
	}

	public Set<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(Set<Integer> groupIds) {
		this.groupIds = groupIds;
	}

	public boolean isSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

}