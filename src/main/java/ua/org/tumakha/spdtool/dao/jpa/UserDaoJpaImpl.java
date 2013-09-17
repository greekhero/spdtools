package ua.org.tumakha.spdtool.dao.jpa;

import java.util.List;
import java.util.Set;

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
		return entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.active != NULL", Long.class)
				.getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findEntries(int firstResult, int maxResults) {
		return entityManager.createQuery("SELECT u FROM User u WHERE u.active != NULL", User.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findAll() {
		return entityManager.createQuery("SELECT u FROM User u WHERE u.active != NULL", User.class).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findActive() {
		return entityManager.createQuery("SELECT u FROM User u WHERE u.active = 1", User.class).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public User findByLastname(String lastname) {
		return entityManager.createQuery("SELECT u FROM User u WHERE u.lastname = ?", User.class)
				.setParameter(1, lastname).getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public User findByLastFirst(String lastname, String firstname) {
		return entityManager.createQuery("SELECT u FROM User u WHERE u.lastname = ? AND u.firstname = ?", User.class)
				.setParameter(1, lastname).setParameter(2, firstname).getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public User findByPIN(Long pin) {
		return entityManager.createQuery("SELECT u FROM User u WHERE u.pin = ?1", User.class).setParameter(1, pin)
				.getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> findByGroup(Integer groupId) {
		return entityManager
				.createQuery("SELECT u FROM User u JOIN u.groups g WHERE u.active != NULL AND g.id = ?", User.class)
				.setParameter(1, groupId).getResultList();
	}

	@Override
	public List<User> findByGroups(List<Integer> groupIds) {
		return entityManager
				.createQuery("SELECT u FROM User u JOIN u.groups g WHERE u.active != NULL AND g.id IN ?1", User.class)
				.setParameter(1, groupIds).getResultList();
	}

	@Override
	public List<User> findActiveUsersByGroups(Set<Integer> groupIds) {
		return entityManager
				.createQuery(
						"SELECT u FROM User u JOIN u.groups g WHERE u.active = 1 AND g.id IN ?1 ORDER BY u.lastname",
						User.class).setParameter(1, groupIds).getResultList();
	}

	@Override
	public List<User> findByIds(Set<Integer> enabledUserIds) {
		return entityManager.createQuery("SELECT u FROM User u WHERE u.active != NULL AND u.userId IN ?1", User.class)
				.setParameter(1, enabledUserIds).getResultList();
	}

}
