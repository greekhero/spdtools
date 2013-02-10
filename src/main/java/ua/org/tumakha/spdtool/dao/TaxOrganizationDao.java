package ua.org.tumakha.spdtool.dao;

import java.util.List;

import ua.org.tumakha.spdtool.entity.TaxOrganization;

/**
 * @author Yuriy Tumakha
 */
public interface TaxOrganizationDao {

	public TaxOrganization find(Object id);

	public void persist(TaxOrganization taxOrganization);

	public TaxOrganization merge(TaxOrganization taxOrganization);

	public void remove(TaxOrganization taxOrganization);

	public long countEntries();

	public List<TaxOrganization> findEntries(int firstResult, int maxResults);

	public List<TaxOrganization> findAll();

}
