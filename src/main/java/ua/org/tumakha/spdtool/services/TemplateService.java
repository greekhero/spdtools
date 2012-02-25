package ua.org.tumakha.spdtool.services;

import java.util.List;

import ua.org.tumakha.spdtool.template.model.ActModel;
import ua.org.tumakha.spdtool.template.model.Form11KvedModel;
import ua.org.tumakha.spdtool.template.model.Form20OPPModel;
import ua.org.tumakha.spdtool.template.model.IncomeCalculationModel;
import ua.org.tumakha.spdtool.template.model.TaxSystemStatementModel;

/**
 * @author Yuriy Tumakha
 */
public interface TemplateService {

	public List<ActModel> getActModelList();

	public List<TaxSystemStatementModel> getTaxSystemStatementModelList(
			Integer groupId);

	public List<IncomeCalculationModel> getIncomeCalculationModelList(
			Integer groupId);

	public List<Form20OPPModel> getForm20OPPModelList();

	public List<Form11KvedModel> getForm11KvedModelList(Integer groupId);

}
