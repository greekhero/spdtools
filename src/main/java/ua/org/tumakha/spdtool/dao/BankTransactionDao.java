package ua.org.tumakha.spdtool.dao;

import ua.org.tumakha.spdtool.entity.BankTransaction;
import ua.org.tumakha.spdtool.entity.User;

import java.util.List;

/**
 * @author Yuriy Tumakha
 */
public interface BankTransactionDao {

	BankTransaction find(Object bankTransactionId);

	BankTransaction findByUserAndId(Integer userId, Long id);

	void persist(BankTransaction transaction);

	BankTransaction merge(BankTransaction transaction);

	void remove(BankTransaction transaction);

	List<BankTransaction> findAll();

    List<BankTransaction> findUserTransactions(User user, Integer year);
}
