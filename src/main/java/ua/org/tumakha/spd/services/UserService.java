package ua.org.tumakha.spd.services;

import java.util.List;

import ua.org.tumakha.spd.entity.User;

/**
 * @author Yuriy Tumakha
 */
public interface UserService {

	public void createUser(User user);

	public User updateUser(User user);

	public User findUserById(Integer userId);

	public List<User> findAllUsers();

}
