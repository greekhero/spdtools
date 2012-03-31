package ua.org.tumakha.spdtool.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.dao.DeclarationDao;
import ua.org.tumakha.spdtool.entity.Declaration;
import ua.org.tumakha.spdtool.services.DeclarationService;

/**
 * @author Yuriy Tumakha
 */
@Service("declarationService")
@Repository
public class DeclarationServiceImpl implements DeclarationService {

	@Autowired
	private DeclarationDao declarationDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createDeclaration(Declaration declaration) {
		declarationDao.persist(declaration);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Declaration updateDeclaration(Declaration declaration) {
		return declarationDao.merge(declaration);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDeclaration(Integer declarationId) {
		Declaration declaration = findDeclaration(declarationId);
		declarationDao.remove(declaration);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Declaration findDeclaration(Integer declarationId) {
		return declarationDao.find(declarationId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Declaration> findAllDeclarations() {
		return declarationDao.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Declaration> findDeclarationsByYearAndQuarter(Integer year,
			Integer quarter) {
		return declarationDao.findByYearAndQuarter(year, quarter);
	}

}
