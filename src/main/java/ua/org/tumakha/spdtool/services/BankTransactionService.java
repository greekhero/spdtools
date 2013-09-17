package ua.org.tumakha.spdtool.services;

import java.util.List;

import ua.org.tumakha.spdtool.entity.BankTransaction;

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

}