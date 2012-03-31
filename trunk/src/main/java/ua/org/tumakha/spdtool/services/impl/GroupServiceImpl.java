package ua.org.tumakha.spdtool.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.GroupDao;
import ua.org.tumakha.spdtool.entity.Group;
import ua.org.tumakha.spdtool.services.GroupService;

/**
 * @author Yuriy Tumakha
 */
@Service("groupService")
@Repository
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createGroup(Group group) {
		groupDao.persist(group);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Group updateGroup(Group group) {
		return groupDao.merge(group);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteGroup(Integer groupId) {
		Group group = findGroup(groupId);
		groupDao.remove(group);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Group findGroup(Integer groupId) {
		return groupDao.find(groupId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public long countGroups() {
		return groupDao.countEntries();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Group> findGroupEntries(int firstResult, int maxResults) {
		return groupDao.findEntries(firstResult, maxResults);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Group> findAllGroups() {
		return groupDao.findAll();
	}

}