package ua.org.tumakha.spdtool.dao;

import java.util.List;
import java.util.Set;

import ua.org.tumakha.spdtool.entity.User;

/**
 * @author Yuriy Tumakha
 */
public interface UserDao {

	User find(Object id);

	void persist(User user);

	User merge(User user);

	long countEntries();

	List<User> findEntries(int firstResult, int maxResults);

	List<User> findAll();

	List<User> findActive();

	User findByLastname(String lastname);

	List<User> findByGroup(Integer groupId);

	List<User> findByGroups(List<Integer> groupIds);

	List<User> findActiveUsersByGroups(Set<Integer> groupIds);

}
