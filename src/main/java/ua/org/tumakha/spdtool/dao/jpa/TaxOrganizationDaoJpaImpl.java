package ua.org.tumakha.spdtool.dao.jpa;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.dao.TaxOrganizationDao;
import ua.org.tumakha.spdtool.entity.TaxOrganization;

/**
 * @author Yuriy Tumakha
 */
@Component("taxOrganizationDao")
public class TaxOrganizationDaoJpaImpl extends AbstractJpaDao<TaxOrganization> implements TaxOrganizationDao {

}
