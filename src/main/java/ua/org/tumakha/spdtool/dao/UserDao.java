package ua.org.tumakha.spdtool.dao;

import java.util.List;

import ua.org.tumakha.spdtool.entity.User;

/**
 * @author Yuriy Tumakha
 */
public interface UserDao {

	public User find(Object id);

	public void persist(User user);

	public User merge(User user);

	public long countEntries();

	public List<User> findEntries(int firstResult, int maxResults);

	public List<User> findAll();

	public List<User> findActive();

	public List<User> findByGroup(Integer groupId);

}
