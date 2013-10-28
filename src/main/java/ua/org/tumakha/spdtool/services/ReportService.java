package ua.org.tumakha.spdtool.services;

import ua.org.tumakha.spdtool.entity.Report;

import java.util.List;

/**
 * @author Yuriy Tumakha
 */
public interface ReportService {

	public void createReport(Report report);

	public Report updateReport(Report report);

	public void deleteReport(Integer reportId);

	public Report findReport(Integer reportId);

	public long countReports();

	public List<Report> findReportEntries(int firstResult, int maxResults);

	public List<Report> findAllReports();

}
