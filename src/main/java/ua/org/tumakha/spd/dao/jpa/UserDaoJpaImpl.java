package ua.org.tumakha.spd.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spd.dao.UserDao;
import ua.org.tumakha.spd.entity.User;

/**
 * @author Yuriy Tumakha
 */
@Component("userDao")
public class UserDaoJpaImpl extends AbstractJpaDao<User> implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		return entityManager.createQuery("SELECT u FROM User u")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findActive() {
		return entityManager.createQuery(
				"SELECT u FROM User u JOIN FETCH u.acts WHERE u.active = 1")
				.getResultList();
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
