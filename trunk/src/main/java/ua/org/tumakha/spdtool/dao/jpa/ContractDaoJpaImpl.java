package ua.org.tumakha.spdtool.dao.jpa;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.dao.ContractDao;
import ua.org.tumakha.spdtool.entity.Contract;

/**
 * @author Yuriy Tumakha
 */
@Component("contractDao")
public class ContractDaoJpaImpl extends AbstractJpaDao<Contract> implements
		ContractDao {

}
