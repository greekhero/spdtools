package ua.org.tumakha.spdtool.services;

import ua.org.tumakha.spdtool.entity.BankTransaction;
import ua.org.tumakha.spdtool.entity.User;

import java.util.List;

/**
 * @author Yuriy Tumakha
 */
public interface BankTransactionService {

	void createTransaction(BankTransaction transaction);

	BankTransaction updateTransaction(BankTransaction transaction);

	void deleteTransaction(Integer transactionId);

	BankTransaction findTransaction(Integer transactionId);

	BankTransaction findTransactionByUserAndId(Integer userId, Long id);

    List<BankTransaction> findAllTransactions();

    List<BankTransaction> findUserTransactions(User user, Integer year);

}