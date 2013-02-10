package ua.org.tumakha.spdtool.dao.jpa;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.dao.AccountDao;
import ua.org.tumakha.spdtool.entity.Account;

/**
 * @author Yuriy Tumakha
 */
@Component("accountDao")
public class AccountDaoJpaImpl extends AbstractJpaDao<Account> implements AccountDao {

}
