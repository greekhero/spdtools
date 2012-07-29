package ua.org.tumakha.spdtool.dao;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Contract;

/**
 * @author Yuriy Tumakha
 */
public interface ContractDao {

	Contract find(Object id);

	void persist(Contract act);

	Contract merge(Contract act);

	void remove(Contract act);

	List<Contract> findAll();

}
