package ua.org.tumakha.spdtool.services;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Kved;

/**
 * @author Yuriy Tumakha
 */
public interface KvedService {

	public void createKved(Kved kved);

	public Kved updateKved(Kved kved);

	public void deleteKved(Integer kvedId);

	public Kved findKved(Integer kvedId);

	public long countKveds();

	public List<Kved> findKvedEntries(int firstResult, int maxResults);

	public List<Kved> findAllKveds();

}
