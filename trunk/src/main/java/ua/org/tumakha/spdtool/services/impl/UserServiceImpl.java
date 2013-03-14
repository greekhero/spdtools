package ua.org.tumakha.spdtool.services.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.UserDao;
import ua.org.tumakha.spdtool.entity.Address;
import ua.org.tumakha.spdtool.entity.Bank;
import ua.org.tumakha.spdtool.entity.Passport;
import ua.org.tumakha.spdtool.entity.ServiceType;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.UserService;

/**
 * @author Yuriy Tumakha
 */
@Service("userService")
@Repository
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

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
		BeanUtils.copyProperties(user.getServiceType(), dbServiceType, getArray("serviceTypeId"));
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
		BeanUtils.copyProperties(user.getAddress(), dbAddress, getArray("addressId"));
		user.setAddress(dbAddress);

		Passport dbPasport = dbUser.getPassport();
		if (dbPasport == null) {
			dbPasport = new Passport();
		}
		BeanUtils.copyProperties(user.getPassport(), dbPasport, getArray("passportId"));
		user.setPassport(dbPasport);

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
	public User findUserByLastname(String lastname) {
		return userDao.findByLastname(lastname);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public User findUserByLastFirst(String lastname, String firstname) {
		return userDao.findByLastFirst(lastname, firstname);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public User findUserByPIN(Long pin) {
		return userDao.findByPIN(pin);
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

	@Override
	public List<User> findUsersByIds(Set<Integer> enabledUserIds) {
		return userDao.findByIds(enabledUserIds);
	}

}