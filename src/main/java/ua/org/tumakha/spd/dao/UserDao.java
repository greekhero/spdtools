package ua.org.tumakha.spd.dao;

import java.util.List;

import ua.org.tumakha.spd.entity.User;

/**
 * @author Yuriy Tumakha
 */
public interface UserDao {

	public User find(Object id);

	public void persist(User user);

	public User merge(User user);

	public List<User> findAll();

	public List<User> findActive();

	public List<User> findByGroup(Integer groupId);

}
