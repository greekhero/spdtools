package ua.org.tumakha.spdtool.dao.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.org.tumakha.spdtool.dao.BankTransactionDao;
import ua.org.tumakha.spdtool.entity.BankTransaction;
import ua.org.tumakha.spdtool.entity.User;

import java.util.List;

/**
 * @author Yuriy Tumakha
 */
@Component("bankTransactionDao")
public class BankTransactionDaoJpaImpl extends AbstractJpaDao<BankTransaction> implements BankTransactionDao {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public BankTransaction findByUserAndId(Integer userId, Long id) {
		List<BankTransaction> resultList = entityManager
				.createQuery("SELECT bt FROM BankTransaction bt WHERE bt.user.userId = ?1 AND bt.id = ?2",
						BankTransaction.class).setParameter(1, userId).setParameter(2, id).getResultList();
		if (resultList != null && resultList.size() == 1) {
			return resultList.get(0);
		} else {
			return null;
		}
	}

    @Override
    public List<BankTransaction> findUserTransactions(User user, Integer year) {
        int from = year * 10000;
        int to = from + 10000;
        return entityManager.createQuery("SELECT bt FROM BankTransaction bt " +
            " WHERE bt.user.userId = ?1 AND bt.bankDate > ?2 AND bt.bankDate < ?3 ORDER BY bt.id",
            BankTransaction.class).setParameter(1, user.getUserId()).setParameter(2, from).setParameter(3, to)
            .getResultList();
    }

}
