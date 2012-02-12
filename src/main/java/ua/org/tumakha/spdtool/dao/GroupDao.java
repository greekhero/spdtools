package ua.org.tumakha.spdtool.dao;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Group;

/**
 * @author Yuriy Tumakha
 */
public interface GroupDao {

	public Group find(Object id);

	public void persist(Group group);

	public Group merge(Group group);

	public void remove(Group group);

	public long countEntries();

	public List<Group> findEntries(int firstResult, int maxResults);

	public List<Group> findAll();

}
