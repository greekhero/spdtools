package ua.org.tumakha.spdtool.dao.jpa;

import org.springframework.stereotype.Component;
import ua.org.tumakha.spdtool.dao.ReportDao;
import ua.org.tumakha.spdtool.entity.Report;
import ua.org.tumakha.spdtool.entity.User;

import java.util.List;

/**
 * @author Yuriy Tumakha
 */
@Component("reportDao")
public class ReportDaoJpaImpl extends AbstractJpaDao<Report> implements ReportDao {

    public List<Report> findAllSorted() {
        return entityManager
                .createQuery("SELECT r FROM Report r WHERE r.enabled = 1 ORDER BY r.sortOrder",
                        Report.class).getResultList();
    }

}
