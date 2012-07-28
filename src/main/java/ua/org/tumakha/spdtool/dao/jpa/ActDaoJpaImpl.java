package ua.org.tumakha.spdtool.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.dao.ActDao;
import ua.org.tumakha.spdtool.entity.Act;

/**
 * @author Yuriy Tumakha
 */
@Component("actDao")
public class ActDaoJpaImpl extends AbstractJpaDao<Act> implements ActDao {

	@Override
	public List<Act> findByYearAndMonth(Integer year, Integer month) {
		String dateParam = String.format("%d-%02d", year, month);
		return entityManager
				.createQuery(
						"SELECT a FROM Act a WHERE SUBSTRING(a.dateFrom, 1, 7) = ?",
						Act.class).setParameter(1, dateParam).getResultList();
	}

}
