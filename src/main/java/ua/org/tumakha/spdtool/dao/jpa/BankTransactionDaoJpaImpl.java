package ua.org.tumakha.spdtool.dao.jpa;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.dao.BankTransactionDao;
import ua.org.tumakha.spdtool.entity.BankTransaction;

/**
 * @author Yuriy Tumakha
 */
@Component("bankTransactionDao")
public class BankTransactionDaoJpaImpl extends AbstractJpaDao<BankTransaction> implements BankTransactionDao {

}
