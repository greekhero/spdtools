package ua.org.tumakha.spd.services;

import java.util.List;

import ua.org.tumakha.spd.template.model.ActModel;
import ua.org.tumakha.spd.template.model.Form20OPPModel;
import ua.org.tumakha.spd.template.model.IncomeCalculationModel;
import ua.org.tumakha.spd.template.model.TaxSystemStatementModel;

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

}
