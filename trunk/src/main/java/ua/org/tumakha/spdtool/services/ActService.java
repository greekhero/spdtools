package ua.org.tumakha.spdtool.services;

import java.util.List;
import java.util.Set;

import ua.org.tumakha.spdtool.entity.Act;

/**
 * @author Yuriy Tumakha
 */
public interface ActService {

	void createAct(Act act);

	Act updateAct(Act act);

	void deleteAct(Integer actId);

	Act findAct(Integer actId);

	List<Act> findAllActs();

	List<Act> findActsByYearAndMonth(Integer year, Integer month);

	void saveActs(List<Act> acts, Set<Integer> enabledUserIds);

}
