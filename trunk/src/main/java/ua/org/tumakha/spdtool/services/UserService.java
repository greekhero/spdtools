package ua.org.tumakha.spdtool.services;

import java.util.List;
import java.util.Set;

import ua.org.tumakha.spdtool.entity.User;

/**
 * @author Yuriy Tumakha
 */
public interface UserService {

	void createUser(User user);

	User updateUser(User user);

	void deleteUser(Integer userId);

	User findUser(Integer userId);

	long countUsers();

	List<User> findUserEntries(int firstResult, int maxResults);

	List<User> findAllUsers();

	List<User> findActiveUsers();

	User findUserByLastname(String lastname);

	List<User> findUsersByGroup(Integer groupId);

	List<User> findUsersByGroups(List<Integer> groupIds);

	List<User> findActiveUsersByGroups(Set<Integer> groupIds);
}
