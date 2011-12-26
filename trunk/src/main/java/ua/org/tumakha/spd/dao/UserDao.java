package ua.org.tumakha.spd.dao;

import java.util.List;

import ua.org.tumakha.spd.entity.User;

/**
 * @author Yuriy Tumakha
 */
public interface UserDao {

	public List<User> findAll();

	public User find(Object id);

	public void persist(User user);

	public User merge(User user);

}
