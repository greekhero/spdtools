package ua.org.tumakha.spdtool.dao;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Kved;

/**
 * @author Yuriy Tumakha
 */
public interface KvedDao {

	public Kved find(Object id);

	public void persist(Kved kved);

	public Kved merge(Kved kved);

	public void remove(Kved kved);

	public long countKveds();

	public List<Kved> findKvedEntries(int firstResult, int maxResults);

	public List<Kved> findAll();

}
