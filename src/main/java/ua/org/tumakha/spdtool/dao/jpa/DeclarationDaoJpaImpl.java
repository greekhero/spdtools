package ua.org.tumakha.spdtool.dao.jpa;

import java.util.List;
import java.util.Set;

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

	@Override
	public List<Declaration> findByYearAndQuarter(Set<Integer> enabledUserIds,
			Integer year, Integer quarter) {
		return entityManager
				.createQuery(
						"SELECT d FROM Declaration d WHERE d.year = ?1 AND d.quarter = ?2 AND d.user.userId IN ?3",
						Declaration.class).setParameter(1, year)
				.setParameter(2, quarter).setParameter(3, enabledUserIds)
				.getResultList();
	}

}
