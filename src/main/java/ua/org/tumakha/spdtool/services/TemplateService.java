package ua.org.tumakha.spdtool.services;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import ua.org.tumakha.spdtool.template.model.ActModel;
import ua.org.tumakha.spdtool.template.model.Form11KvedModel;
import ua.org.tumakha.spdtool.template.model.Form20OPPModel;
import ua.org.tumakha.spdtool.template.model.IncomeCalculationModel;
import ua.org.tumakha.spdtool.template.model.TaxSystemStatementModel;
import freemarker.template.TemplateException;

/**
 * @author Yuriy Tumakha
 */
public interface TemplateService {

	List<ActModel> getActModelList();

	List<TaxSystemStatementModel> getTaxSystemStatementModelList(Integer groupId);

	List<IncomeCalculationModel> getIncomeCalculationModelList(Integer groupId);

	List<Form20OPPModel> getForm20OPPModelList();

	List<Form11KvedModel> getForm11KvedModelList(Integer groupId);

	List<String> generateDeclarations(Set<Integer> groupIds, Integer year,
			Integer quarter) throws InvalidFormatException, IOException;

	List<String> generateActs(Integer year, Integer month)
			throws JAXBException, Docx4JException, TemplateException,
			IOException;

}
