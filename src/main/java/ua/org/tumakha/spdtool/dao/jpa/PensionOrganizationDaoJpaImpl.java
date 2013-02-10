package ua.org.tumakha.spdtool.dao.jpa;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.dao.PensionOrganizationDao;
import ua.org.tumakha.spdtool.entity.PensionOrganization;

/**
 * @author Yuriy Tumakha
 */
@Component("pensionOrganizationDao")
public class PensionOrganizationDaoJpaImpl extends AbstractJpaDao<PensionOrganization> implements
		PensionOrganizationDao {

}
