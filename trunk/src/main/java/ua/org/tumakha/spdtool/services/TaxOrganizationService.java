package ua.org.tumakha.spdtool.services;

import java.util.List;

import ua.org.tumakha.spdtool.entity.TaxOrganization;

/**
 * @author Yuriy Tumakha
 */
public interface TaxOrganizationService {

	public void createTaxOrganization(TaxOrganization taxOrganization);

	public TaxOrganization updateTaxOrganization(TaxOrganization taxOrganization);

	public void deleteTaxOrganization(Integer taxOrganizationId);

	public TaxOrganization findTaxOrganization(Integer taxOrganizationId);

	public long countTaxOrganizations();

	public List<TaxOrganization> findTaxOrganizationEntries(int firstResult, int maxResults);

	public List<TaxOrganization> findAllTaxOrganizations();

}
