package ua.org.tumakha.spdtool.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.KvedDao;
import ua.org.tumakha.spdtool.entity.Kved;

/**
 * @author Yuriy Tumakha
 */
@Component("kvedDao")
public class KvedDaoJpaImpl extends AbstractJpaDao<Kved> implements KvedDao {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public long countKveds() {
		return entityManager.createQuery("SELECT COUNT(k) FROM Kved k",
				Long.class).getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Kved> findKvedEntries(int firstResult, int maxResults) {
		return entityManager.createQuery("SELECT k FROM Kved k", Kved.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Kved> findAll() {
		return entityManager.createQuery("SELECT k FROM Kved k", Kved.class)
				.getResultList();
	}
}
