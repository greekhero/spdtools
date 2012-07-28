package ua.org.tumakha.spdtool.services.impl;

import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.ActDao;
import ua.org.tumakha.spdtool.entity.Act;
import ua.org.tumakha.spdtool.services.ActService;

/**
 * @author Yuriy Tumakha
 */
@Service("actService")
@Repository
public class ActServiceImpl implements ActService {

	@Autowired
	private ActDao actDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createAct(Act act) {
		actDao.persist(act);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Act updateAct(Act act) {
		return actDao.merge(act);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAct(Integer actId) {
		Act act = findAct(actId);
		actDao.remove(act);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Act findAct(Integer actId) {
		return actDao.find(actId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Act> findAllActs() {
		return actDao.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Act> findActsByYearAndMonth(Integer year, Integer month) {
		return actDao.findByYearAndMonth(year, month);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveActs(List<Act> acts) {
		notNull(acts);
		for (Act act : acts) {
			if (act.getActId() == null) {
				actDao.persist(act);
			} else {
				actDao.merge(act);
			}
		}
	}

}
