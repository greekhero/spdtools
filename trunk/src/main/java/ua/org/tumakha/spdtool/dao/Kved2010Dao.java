package ua.org.tumakha.spdtool.dao;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Kved2010;

/**
 * @author Yuriy Tumakha
 */
public interface Kved2010Dao {

	public Kved2010 find(Object id);

	public void persist(Kved2010 kved);

	public Kved2010 merge(Kved2010 kved);

	public void remove(Kved2010 kved);

	public long countEntries();

	public List<Kved2010> findEntries(int firstResult, int maxResults);

	public List<Kved2010> findAll();

}
