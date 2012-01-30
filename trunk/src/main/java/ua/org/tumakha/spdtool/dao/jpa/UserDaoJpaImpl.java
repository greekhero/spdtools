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
		return entityManager.createQuery(
				"SELECT COUNT(u) FROM User u WHERE u.active != NULL",
				Long.class).getSingleResult();
	}

	@Override
	public List<User> findUserEntries(int firstResult, int maxResults) {
		return entityManager
				.createQuery("SELECT u FROM User u WHERE u.active != NULL",
						User.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	@Override
	public List<User> findAll() {
		return entityManager.createQuery(
				"SELECT u FROM User u WHERE u.active != NULL", User.class)
				.getResultList();
	}

	@Override
	public List<User> findActive() {
		return entityManager.createQuery(
				"SELECT u FROM User u WHERE u.active = 1", User.class)
				.getResultList();
	}

	@Override
	public List<User> findByGroup(Integer groupId) {
		return entityManager
				.createQuery(
						"SELECT u FROM User u JOIN u.groups g WHERE u.active != NULL AND g.id = ?",
						User.class).setParameter(1, groupId).getResultList();
	}

}
