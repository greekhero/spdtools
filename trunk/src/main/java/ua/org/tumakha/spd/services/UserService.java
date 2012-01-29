package ua.org.tumakha.spd.services;

import java.util.List;

import ua.org.tumakha.spd.entity.User;

/**
 * @author Yuriy Tumakha
 */
public interface UserService {

	public void createUser(User user);

	public User updateUser(User user);

	public void deleteUser(Integer userId);

	public User findUser(Integer userId);

	public long countUsers();

	public List<User> findUserEntries(int firstResult, int maxResults);

	public List<User> findAllUsers();

	public List<User> findActiveUsers();

	public List<User> findUsersByGroup(Integer groupId);

}
