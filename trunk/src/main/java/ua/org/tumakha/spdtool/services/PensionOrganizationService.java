package ua.org.tumakha.spdtool.services;

import java.util.List;

import ua.org.tumakha.spdtool.entity.PensionOrganization;

/**
 * @author Yuriy Tumakha
 */
public interface PensionOrganizationService {

	public void createPensionOrganization(PensionOrganization pensionOrganization);

	public PensionOrganization updatePensionOrganization(PensionOrganization pensionOrganization);

	public void deletePensionOrganization(Integer pensionOrganizationId);

	public PensionOrganization findPensionOrganization(Integer pensionOrganizationId);

	public long countPensionOrganizations();

	public List<PensionOrganization> findPensionOrganizationEntries(int firstResult, int maxResults);

	public List<PensionOrganization> findAllPensionOrganizations();

}
