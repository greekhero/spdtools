package ua.org.tumakha.spd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spd.dao.UserDao;
import ua.org.tumakha.spd.entity.User;
import ua.org.tumakha.spd.services.UserService;

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

    @Transactional(propagation = Propagation.REQUIRED)
    public void createUser(User user) {
        userDao.persist(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void removeUser(User user) {
        userDao.remove(user);
    }

}
