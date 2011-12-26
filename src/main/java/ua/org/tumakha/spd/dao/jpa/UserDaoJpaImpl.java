package ua.org.tumakha.spd.dao.jpa;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spd.dao.UserDao;
import ua.org.tumakha.spd.entity.User;

/**
 * @author Yuriy Tumakha
 */
@Component("userDao")
public class UserDaoJpaImpl extends AbstractJpaDao<User> implements UserDao {

    @Override
    public void remove(User user) {
        // TODO only set flag removed
        user = find(user.getUserId());
        super.remove(user);
    }

}
