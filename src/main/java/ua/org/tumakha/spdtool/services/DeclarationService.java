package ua.org.tumakha.spdtool.services;

import java.util.List;

import ua.org.tumakha.spdtool.entity.Declaration;

/**
 * @author Yuriy Tumakha
 */
public interface DeclarationService {

	void createDeclaration(Declaration declaration);

	Declaration updateDeclaration(Declaration declaration);

	void deleteDeclaration(Integer declarationId);

	Declaration findDeclaration(Integer declarationId);

	List<Declaration> findAllDeclarations();

	List<Declaration> findDeclarationsByYearAndQuarter(Integer year,
			Integer quarter);

}
