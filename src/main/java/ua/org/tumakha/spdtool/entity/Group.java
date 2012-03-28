package ua.org.tumakha.spdtool.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "user_group")
public class Group implements Serializable {

	private static final long serialVersionUID = 1308429568785478009L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer groupId;

	private String name;

	@ManyToMany(mappedBy = "groups")
	private Set<User> users;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
