package ua.org.tumakha.spdtool.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.UserDao;
import ua.org.tumakha.spdtool.entity.User;

/**
 * @author Yuriy Tumakha
 */
@Component("userDao")
public class UserDaoJpaImpl extends AbstractJpaDao<User> implements UserDao {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public long countEntries() {
		return entityManager.createQuery(
				"SELECT COUNT(u) FROM User u WHERE u.active != NULL",
				Long.class).getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findEntries(int firstResult, int maxResults) {
		return entityManager
				.createQuery("SELECT u FROM User u WHERE u.active != NULL",
						User.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findAll() {
		return entityManager.createQuery(
				"SELECT u FROM User u WHERE u.active != NULL", User.class)
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findActive() {
		return entityManager.createQuery(
				"SELECT u FROM User u WHERE u.active = 1", User.class)
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findByGroup(Integer groupId) {
		return entityManager
				.createQuery(
						"SELECT u FROM User u JOIN u.groups g WHERE u.active != NULL AND g.id = ?",
						User.class).setParameter(1, groupId).getResultList();
	}

}
