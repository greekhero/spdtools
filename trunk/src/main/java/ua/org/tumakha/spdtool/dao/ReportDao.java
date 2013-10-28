package ua.org.tumakha.spdtool.dao;

import ua.org.tumakha.spdtool.entity.Report;

import java.util.List;

/**
 * @author Yuriy Tumakha
 */
public interface ReportDao {

	public Report find(Object id);

	public void persist(Report Report);

	public Report merge(Report Report);

	public void remove(Report Report);

	public long countEntries();

	public List<Report> findEntries(int firstResult, int maxResults);

	public List<Report> findAll();

    public List<Report> findAllSorted();
}
