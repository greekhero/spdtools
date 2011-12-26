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
		return entityManager.createQuery("select u from User u")
				.getResultList();
	}

}
