package ua.org.tumakha.spdtool.services;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Kved2010;

/**
 * @author Yuriy Tumakha
 */
public interface Kved2010Service {

	public void createKved(Kved2010 kved);

	public Kved2010 updateKved(Kved2010 kved);

	public void deleteKved(Integer kvedId);

	public Kved2010 findKved(Integer kvedId);

	public long countKveds();

	public List<Kved2010> findKvedEntries(int firstResult, int maxResults);

	public List<Kved2010> findAllKveds();

}
