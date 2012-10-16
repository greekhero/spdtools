package ua.org.tumakha.spdtool.services;

import java.util.List;
import java.util.Set;

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

	List<Declaration> findDeclarationsByYearAndQuarter(
			Set<Integer> enabledUserIds, Integer year, Integer quarter);

	void saveDeclarations(Set<Integer> enabledUserIds,
			List<Declaration> declarations);

}
