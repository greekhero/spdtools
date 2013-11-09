package ua.org.tumakha.spdtool.services;

import freemarker.template.TemplateException;
import org.apache.fop.apps.FOPException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import ua.org.tumakha.spdtool.template.model.*;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author Yuriy Tumakha
 */
public interface TemplateService {

	List<ActModel> getActModelList();

	List<TaxSystemStatementModel> getTaxSystemStatementModelList(Integer groupId);

	List<IncomeCalculationModel> getIncomeCalculationModelList(Integer groupId);

	List<Form20OPPModel> getForm20OPPModelList();

	List<Form11KvedModel> getForm11KvedModelList(Integer groupId);

	List<String> generateDeclarations(Set<Integer> enabledUserIds, Set<Integer> groupIds, Integer year, Integer quarter)
			throws InvalidFormatException, IOException;

	List<String> generateActs(Set<Integer> enabledUserIds, Integer year, Integer month, boolean generateContracts,
			boolean generateActs) throws JAXBException, Docx4JException, TemplateException, IOException,
            TransformerException, FOPException, ExecutionException, InterruptedException;

	List<String> generateEcpDocuments(Set<Integer> enabledUserIds, Set<Integer> groupIds, Date date)
			throws JAXBException, Docx4JException, TemplateException, IOException, InvalidFormatException;

	List<String> generatePaymentDocuments(Set<Integer> enabledUserIds, boolean sendEmail)
			throws InvalidFormatException, IOException;

    List<String> generateBankExtract(Set<Integer> enabledUserIds, Integer year) throws IOException, InvalidFormatException;
}
