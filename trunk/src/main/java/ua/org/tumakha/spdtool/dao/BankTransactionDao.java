package ua.org.tumakha.spdtool.dao;

import java.util.List;

import ua.org.tumakha.spdtool.entity.BankTransaction;

/**
 * @author Yuriy Tumakha
 */
public interface BankTransactionDao {

	BankTransaction find(Object id);

	BankTransaction findByUserAndId(Integer userId, Integer id);

	void persist(BankTransaction transaction);

	BankTransaction merge(BankTransaction transaction);

	void remove(BankTransaction transaction);

	List<BankTransaction> findAll();

}
