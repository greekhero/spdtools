package ua.org.tumakha.spdtool.web.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import ua.org.tumakha.spdtool.entity.User;

public class EcpModel implements Serializable {

	private static final long serialVersionUID = 1308953328548550645L;

	@Size(min = 1)
	@NotNull
	private Set<Integer> groupIds;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	private Date date;

	private List<User> users;

	private Set<Integer> enabledUserIds;

	public EcpModel() {
		enabledUserIds = new HashSet<Integer>();
	}

	public Set<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(Set<Integer> groupIds) {
		this.groupIds = groupIds;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

}
