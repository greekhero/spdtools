package ua.org.tumakha.spdtool.dao.jpa;

import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.dao.GroupDao;
import ua.org.tumakha.spdtool.entity.Group;

/**
 * @author Yuriy Tumakha
 */
@Component("groupDao")
public class GroupDaoJpaImpl extends AbstractJpaDao<Group> implements GroupDao {

}
