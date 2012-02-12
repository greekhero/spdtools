package ua.org.tumakha.spdtool.dao.jpa;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.dao.KvedDao;
import ua.org.tumakha.spdtool.entity.Kved;

/**
 * @author Yuriy Tumakha
 */
@Component("kvedDao")
public class KvedDaoJpaImpl extends AbstractJpaDao<Kved> implements KvedDao {

}
