package ua.org.tumakha.spd.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spd.dao.UserDao;
import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.services.UserService;

/**
 * @author Yuriy Tumakha
 */
@Service("userService")
@Repository
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	@Required
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createUser(User user) {
		userDao.persist(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User updateUser(User user) {
		return userDao.merge(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(Integer userId) {
		User user = findUser(userId);
		user.setDeleted(true);
		userDao.merge(user);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public User findUser(Integer userId) {
		return userDao.find(userId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public long countUsers() {
		return userDao.countUsers();
	}

	@Override
	public List<User> findUserEntries(int firstResult, int maxResults) {
		return userDao.findUserEntries(firstResult, maxResults);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findAllUsers() {
		return userDao.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findActiveUsers() {
		return userDao.findActive();
	}

	@Override
	public List<User> findUsersByGroup(Integer groupId) {
		return userDao.findByGroup(groupId);
	}

}