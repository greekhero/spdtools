package ua.org.tumakha.spdtool.dao;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Declaration;

/**
 * @author Yuriy Tumakha
 */
public interface DeclarationDao {

	Declaration find(Object id);

	void persist(Declaration declaration);

	Declaration merge(Declaration declaration);

	void remove(Declaration declaration);

	List<Declaration> findAll();

	List<Declaration> findByYearAndQuarter(Integer year, Integer quarter);

}
