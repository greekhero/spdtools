package ua.org.tumakha.spdtool.services;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Group;

/**
 * @author Yuriy Tumakha
 */
public interface GroupService {

	public void createGroup(Group group);

	public Group updateGroup(Group group);

	public void deleteGroup(Integer groupId);

	public Group findGroup(Integer groupId);

	public long countGroups();

	public List<Group> findGroupEntries(int firstResult, int maxResults);

	public List<Group> findAllGroups();

}
