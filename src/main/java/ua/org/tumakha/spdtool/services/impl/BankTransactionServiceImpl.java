package ua.org.tumakha.spdtool.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.org.tumakha.spdtool.dao.BankTransactionDao;
import ua.org.tumakha.spdtool.entity.BankTransaction;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.services.BankTransactionService;

import java.util.List;

/**
 * @author Yuriy Tumakha
 */
@Service("bankTransactionService")
@Repository
public class BankTransactionServiceImpl implements BankTransactionService {

	@Autowired
	private BankTransactionDao bankTransactionDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createTransaction(BankTransaction transaction) {
		bankTransactionDao.persist(transaction);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public BankTransaction updateTransaction(BankTransaction transaction) {
		return bankTransactionDao.merge(transaction);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTransaction(Integer transactionId) {
		BankTransaction transaction = findTransaction(transactionId);
		bankTransactionDao.remove(transaction);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public BankTransaction findTransaction(Integer transactionId) {
		return bankTransactionDao.find(transactionId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public BankTransaction findTransactionByUserAndId(Integer userId, Long id) {
		return bankTransactionDao.findByUserAndId(userId, id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<BankTransaction> findAllTransactions() {
		return bankTransactionDao.findAll();
	}

    @Override
    public List<BankTransaction> findUserTransactions(User user, Integer year) {
        return bankTransactionDao.findUserTransactions(user, year);
    }

}