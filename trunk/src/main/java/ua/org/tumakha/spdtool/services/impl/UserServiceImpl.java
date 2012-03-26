package ua.org.tumakha.spdtool.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.UserDao;
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
		ServiceType serviceType = user.getServiceType();
		dbServiceType.setName(serviceType.getName());
		dbServiceType.setNameEn(serviceType.getNameEn());
		user.setServiceType(dbServiceType);

		Bank dbBank = dbUser.getBank();
		if (dbBank == null) {
			dbBank = new Bank();
		}
		Bank bank = user.getBank();
		dbBank.setAccountNumber(bank.getAccountNumber());
		dbBank.setMFO(bank.getMFO());
		dbBank.setSWIFT(bank.getSWIFT());
		dbBank.setName(bank.getName());
		dbBank.setNameEn(bank.getNameEn());
		dbBank.setUsedMiddlename(bank.isUsedMiddlename());
		dbBank.setCorrespondentBank(bank.getCorrespondentBank());
		dbBank.setCorrespondentBankEn(bank.getCorrespondentBankEn());
		user.setBank(dbBank);

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

}