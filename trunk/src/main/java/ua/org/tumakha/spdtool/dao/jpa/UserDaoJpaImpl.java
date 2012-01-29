package ua.org.tumakha.spdtool.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.dao.UserDao;
import ua.org.tumakha.spdtool.entity.User;

/**
 * @author Yuriy Tumakha
 */
@Component("userDao")
public class UserDaoJpaImpl extends AbstractJpaDao<User> implements UserDao {

	@Override
	public long countUsers() {
		return (Long) entityManager
				.createQuery(
						"SELECT COUNT(u) FROM User u WHERE u.active = 1 OR u.active = 0")
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserEntries(int firstResult, int maxResults) {
		return entityManager
				.createQuery(
						"SELECT u FROM User u WHERE u.active = 1 OR u.active = 0")
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		return entityManager.createQuery(
				"SELECT u FROM User u WHERE u.active = 1 OR u.active = 0")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findActive() {
		return entityManager.createQuery(
				"SELECT u FROM User u WHERE u.active = 1").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByGroup(Integer groupId) {
		return entityManager
				.createQuery(
						"SELECT u FROM User u JOIN u.groups g WHERE g.id = ?")
				.setParameter(1, groupId).getResultList();
	}

}
