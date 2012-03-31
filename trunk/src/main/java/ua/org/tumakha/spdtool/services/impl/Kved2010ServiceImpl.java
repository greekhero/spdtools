package ua.org.tumakha.spdtool.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.Kved2010Dao;
import ua.org.tumakha.spdtool.entity.Kved2010;
import ua.org.tumakha.spdtool.services.Kved2010Service;

/**
 * @author Yuriy Tumakha
 */
@Service("kved2010Service")
@Repository
public class Kved2010ServiceImpl implements Kved2010Service {

	@Autowired
	private Kved2010Dao kved2010Dao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createKved(Kved2010 kved) {
		kved2010Dao.persist(kved);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Kved2010 updateKved(Kved2010 kved) {
		return kved2010Dao.merge(kved);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteKved(Integer kvedId) {
		Kved2010 kved = findKved(kvedId);
		kved2010Dao.remove(kved);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Kved2010 findKved(Integer kvedId) {
		return kved2010Dao.find(kvedId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public long countKveds() {
		return kved2010Dao.countEntries();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Kved2010> findKvedEntries(int firstResult, int maxResults) {
		return kved2010Dao.findEntries(firstResult, maxResults);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Kved2010> findAllKveds() {
		return kved2010Dao.findAll();
	}

}