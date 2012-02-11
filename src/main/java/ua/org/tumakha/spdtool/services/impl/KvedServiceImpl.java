package ua.org.tumakha.spdtool.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.KvedDao;
import ua.org.tumakha.spdtool.entity.Kved;
import ua.org.tumakha.spdtool.services.KvedService;

/**
 * @author Yuriy Tumakha
 */
@Service("kvedService")
@Repository
public class KvedServiceImpl implements KvedService {

	private KvedDao kvedDao;

	@Required
	@Autowired
	public void setKvedDao(KvedDao kvedDao) {
		this.kvedDao = kvedDao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createKved(Kved kved) {
		kvedDao.persist(kved);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Kved updateKved(Kved kved) {
		return kvedDao.merge(kved);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteKved(Integer kvedId) {
		Kved kved = findKved(kvedId);
		kvedDao.remove(kved);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Kved findKved(Integer kvedId) {
		return kvedDao.find(kvedId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public long countKveds() {
		return kvedDao.countKveds();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Kved> findKvedEntries(int firstResult, int maxResults) {
		return kvedDao.findKvedEntries(firstResult, maxResults);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Kved> findAllKveds() {
		return kvedDao.findAll();
	}

}