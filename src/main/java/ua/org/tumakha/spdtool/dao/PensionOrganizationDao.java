package ua.org.tumakha.spdtool.dao;

import java.util.List;

import ua.org.tumakha.spdtool.entity.PensionOrganization;

/**
 * @author Yuriy Tumakha
 */
public interface PensionOrganizationDao {

	public PensionOrganization find(Object id);

	public void persist(PensionOrganization pensionOrganization);

	public PensionOrganization merge(PensionOrganization pensionOrganization);

	public void remove(PensionOrganization pensionOrganization);

	public long countEntries();

	public List<PensionOrganization> findEntries(int firstResult, int maxResults);

	public List<PensionOrganization> findAll();

}
