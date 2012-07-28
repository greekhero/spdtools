package ua.org.tumakha.spdtool.dao;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Act;

/**
 * @author Yuriy Tumakha
 */
public interface ActDao {

	Act find(Object id);

	void persist(Act act);

	Act merge(Act act);

	void remove(Act act);

	List<Act> findAll();

	List<Act> findByYearAndMonth(Integer year, Integer month);

}
