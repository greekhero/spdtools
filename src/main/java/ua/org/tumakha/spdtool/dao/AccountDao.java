package ua.org.tumakha.spdtool.dao;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Account;

/**
 * @author Yuriy Tumakha
 */
public interface AccountDao {

	Account find(Object id);

	void persist(Account acount);

	Account merge(Account acount);

	void remove(Account acount);

	List<Account> findAll();

}
