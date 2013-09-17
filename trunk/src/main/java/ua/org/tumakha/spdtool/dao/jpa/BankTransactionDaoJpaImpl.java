package ua.org.tumakha.spdtool.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.BankTransactionDao;
import ua.org.tumakha.spdtool.entity.BankTransaction;

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

}
