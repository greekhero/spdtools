package ua.org.tumakha.spdtool.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.org.tumakha.spdtool.dao.ReportDao;
import ua.org.tumakha.spdtool.entity.Report;
import ua.org.tumakha.spdtool.services.ReportService;

import java.util.List;

/**
 * @author Yuriy Tumakha
 */
@Service("reportService")
@Repository
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDao reportDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createReport(Report report) {
		reportDao.persist(report);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Report updateReport(Report report) {
		return reportDao.merge(report);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteReport(Integer reportId) {
		Report report = findReport(reportId);
		reportDao.remove(report);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Report findReport(Integer reportId) {
		return reportDao.find(reportId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public long countReports() {
		return reportDao.countEntries();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Report> findReportEntries(int firstResult, int maxResults) {
		return reportDao.findEntries(firstResult, maxResults);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Report> findAllReports() {
		return reportDao.findAllSorted();
	}

}