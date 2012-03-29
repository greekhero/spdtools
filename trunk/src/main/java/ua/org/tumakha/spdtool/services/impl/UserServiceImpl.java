package ua.org.tumakha.spdtool.services.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.UserDao;
import ua.org.tumakha.spdtool.entity.Address;
import ua.org.tumakha.spdtool.entity.Bank;
import ua.org.tumakha.spdtool.entity.ServiceType;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.UserService;

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
		// TODO: re-attach user serviceType, bank, address
		User dbUser = findUser(user.getUserId());
		ServiceType dbServiceType = dbUser.getServiceType();
		if (dbServiceType == null) {
			dbServiceType = new ServiceType();
		}
		BeanUtils.copyProperties(user.getServiceType(), dbServiceType,
				getArray("serviceTypeId"));
		user.setServiceType(dbServiceType);

		Bank dbBank = dbUser.getBank();
		if (dbBank == null) {
			dbBank = new Bank();
		}
		BeanUtils.copyProperties(user.getBank(), dbBank, getArray("bankId"));
		user.setBank(dbBank);

		Address dbAddress = dbUser.getAddress();
		if (dbAddress == null) {
			dbAddress = new Address();
		}
		BeanUtils.copyProperties(user.getAddress(), dbAddress,
				getArray("addressId"));
		user.setAddress(dbAddress);

		return userDao.merge(user);
	}

	private String[] getArray(String value) {
		return new String[] { value };
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
		return userDao.countEntries();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findUserEntries(int firstResult, int maxResults) {
		return userDao.findEntries(firstResult, maxResults);
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
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findUsersByGroup(Integer groupId) {
		return userDao.findByGroup(groupId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findUsersByGroups(List<Integer> groupIds) {
		return userDao.findByGroups(groupIds);
	}

	@Override
	public List<User> findActiveUsersByGroups(Set<Integer> groupIds) {
		return userDao.findActiveUsersByGroups(groupIds);
	}

}