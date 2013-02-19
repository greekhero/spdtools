package ua.org.tumakha.spdtool.dao.jpa;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
		// String dateParam = String.format("%d-%02d", year, month);
		// return entityManager
		// .createQuery(
		// "SELECT a FROM Act a WHERE SUBSTRING(a.dateFrom, 1, 7) = ?",
		// Act.class).setParameter(1, dateParam).getResultList();
		Calendar calendarEndActStart = new GregorianCalendar(year, month - 1, 1);
		int nextMonth = month == 12 ? 0 : month;
		int nextYear = month == 12 ? year + 1 : year;
		Calendar calendarEndActFinish = new GregorianCalendar(nextYear, nextMonth, 25);
		return entityManager.createQuery("SELECT a FROM Act a WHERE a.dateTo > ? AND a.dateTo < ?", Act.class)
				.setParameter(1, calendarEndActStart.getTime()).setParameter(2, calendarEndActFinish.getTime())
				.getResultList();
	}

}
