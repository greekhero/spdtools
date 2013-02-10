package ua.org.tumakha.spdtool.services.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.TaxOrganizationDao;
import ua.org.tumakha.spdtool.entity.Account;
import ua.org.tumakha.spdtool.entity.TaxOrganization;
import ua.org.tumakha.spdtool.services.TaxOrganizationService;

/**
 * @author Yuriy Tumakha
 */
@Service("taxOrganizationService")
@Repository
public class TaxOrganizationServiceImpl implements TaxOrganizationService {

	@Autowired
	private TaxOrganizationDao taxOrganizationDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createTaxOrganization(TaxOrganization taxOrganization) {
		taxOrganizationDao.persist(taxOrganization);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public TaxOrganization updateTaxOrganization(TaxOrganization taxOrganization) {
		TaxOrganization dbTaxOrganization = findTaxOrganization(taxOrganization.getOrganizationId());
		Account dbAccount = dbTaxOrganization.getAccount();
		if (dbAccount == null) {
			dbAccount = new Account();
		}
		BeanUtils.copyProperties(taxOrganization.getAccount(), dbAccount, getArray("accountId"));
		taxOrganization.setAccount(dbAccount);
		return taxOrganizationDao.merge(taxOrganization);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTaxOrganization(Integer taxOrganizationId) {
		TaxOrganization taxOrganization = findTaxOrganization(taxOrganizationId);
		taxOrganizationDao.remove(taxOrganization);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public TaxOrganization findTaxOrganization(Integer taxOrganizationId) {
		return taxOrganizationDao.find(taxOrganizationId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public long countTaxOrganizations() {
		return taxOrganizationDao.countEntries();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<TaxOrganization> findTaxOrganizationEntries(int firstResult, int maxResults) {
		return taxOrganizationDao.findEntries(firstResult, maxResults);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<TaxOrganization> findAllTaxOrganizations() {
		return taxOrganizationDao.findAll();
	}

	private String[] getArray(String value) {
		return new String[] { value };
	}
}