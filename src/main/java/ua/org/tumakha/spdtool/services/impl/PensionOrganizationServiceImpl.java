package ua.org.tumakha.spdtool.services.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.PensionOrganizationDao;
import ua.org.tumakha.spdtool.entity.Account;
import ua.org.tumakha.spdtool.entity.PensionOrganization;
import ua.org.tumakha.spdtool.services.PensionOrganizationService;

/**
 * @author Yuriy Tumakha
 */
@Service("pensionOrganizationService")
@Repository
public class PensionOrganizationServiceImpl implements PensionOrganizationService {

	@Autowired
	private PensionOrganizationDao pensionOrganizationDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createPensionOrganization(PensionOrganization pensionOrganization) {
		pensionOrganizationDao.persist(pensionOrganization);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public PensionOrganization updatePensionOrganization(PensionOrganization pensionOrganization) {
		PensionOrganization dbPensionOrganization = findPensionOrganization(pensionOrganization.getOrganizationId());
		Account dbAccount = dbPensionOrganization.getAccount();
		if (dbAccount == null) {
			dbAccount = new Account();
		}
		BeanUtils.copyProperties(pensionOrganization.getAccount(), dbAccount, getArray("accountId"));
		pensionOrganization.setAccount(dbAccount);
		return pensionOrganizationDao.merge(pensionOrganization);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePensionOrganization(Integer pensionOrganizationId) {
		PensionOrganization pensionOrganization = findPensionOrganization(pensionOrganizationId);
		pensionOrganizationDao.remove(pensionOrganization);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public PensionOrganization findPensionOrganization(Integer pensionOrganizationId) {
		return pensionOrganizationDao.find(pensionOrganizationId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public long countPensionOrganizations() {
		return pensionOrganizationDao.countEntries();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PensionOrganization> findPensionOrganizationEntries(int firstResult, int maxResults) {
		return pensionOrganizationDao.findEntries(firstResult, maxResults);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PensionOrganization> findAllPensionOrganizations() {
		return pensionOrganizationDao.findAll();
	}

	private String[] getArray(String value) {
		return new String[] { value };
	}

}