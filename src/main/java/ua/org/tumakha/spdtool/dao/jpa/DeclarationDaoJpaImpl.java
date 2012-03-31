package ua.org.tumakha.spdtool.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.dao.DeclarationDao;
import ua.org.tumakha.spdtool.entity.Declaration;

/**
 * @author Yuriy Tumakha
 */
@Component("declarationDao")
public class DeclarationDaoJpaImpl extends AbstractJpaDao<Declaration>
		implements DeclarationDao {

	@Override
	public List<Declaration> findByYearAndQuarter(Integer year, Integer quarter) {
		return entityManager
				.createQuery(
						"SELECT d FROM Declaration d WHERE d.year = ? AND d.quarter = ?",
						Declaration.class).setParameter(1, year)
				.setParameter(2, quarter).getResultList();
	}

}
