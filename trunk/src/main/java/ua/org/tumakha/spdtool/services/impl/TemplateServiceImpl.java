package ua.org.tumakha.spdtool.services.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fop.apps.FOPException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.org.tumakha.spdtool.entity.Act;
import ua.org.tumakha.spdtool.entity.Declaration;
import ua.org.tumakha.spdtool.entity.Kved2010;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.enums.RentType;
import ua.org.tumakha.spdtool.services.ActService;
import ua.org.tumakha.spdtool.services.DeclarationService;
import ua.org.tumakha.spdtool.services.TemplateService;
import ua.org.tumakha.spdtool.services.UserService;
import ua.org.tumakha.spdtool.template.DocxProcessor;
import ua.org.tumakha.spdtool.template.DocxTemplate;
import ua.org.tumakha.spdtool.template.FOProcessor;
import ua.org.tumakha.spdtool.template.FOTemplate;
import ua.org.tumakha.spdtool.template.FOType;
import ua.org.tumakha.spdtool.template.TextProcessor;
import ua.org.tumakha.spdtool.template.TextTemplate;
import ua.org.tumakha.spdtool.template.XlsProcessor;
import ua.org.tumakha.spdtool.template.XlsTemplate;
import ua.org.tumakha.spdtool.template.model.ActModel;
import ua.org.tumakha.spdtool.template.model.Form11KvedModel;
import ua.org.tumakha.spdtool.template.model.Form20OPPModel;
import ua.org.tumakha.spdtool.template.model.IncomeCalculationModel;
import ua.org.tumakha.spdtool.template.model.TaxSystemStatementModel;
import ua.org.tumakha.spdtool.template.model.UserModel;
import ua.org.tumakha.util.StrUtil;
import freemarker.template.TemplateException;

/**
 * @author Yuriy Tumakha
 */
@Service("templateService")
@Repository
public class TemplateServiceImpl implements TemplateService {

	private static final Log log = LogFactory.getLog(TemplateServiceImpl.class);
	private static final Locale UA_LOCALE = new Locale("uk", "UA");

	private static final NumberFormat MONEY_FORMAT = getMoneyFormat();
	private static final DateFormat UA_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	private static final DateFormat DAY_MONTH_EN_FORMAT = new SimpleDateFormat("d'th of 'MMMMM", Locale.US);

	private static final DateFormat DAY_FORMAT = new SimpleDateFormat("dd");
	private static final DateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
	private static final DateFormat UA_MONTH_FORMAT = new SimpleDateFormat("MMMMM", UA_LOCALE);

	@Autowired
	private UserService userService;

	@Autowired
	private DeclarationService declarationService;

	@Autowired
	private ActService actService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${admin.email}")
	private String adminEmail;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	@Deprecated
	public List<ActModel> getActModelList() {
		List<User> users = userService.findActiveUsers();
		if (users != null && users.size() > 0) {
			List<ActModel> listModel = new ArrayList<ActModel>(users.size());
			User lastUser = null;
			try {
				for (User user : users) {
					if (!user.getUserId().equals(93)) {
						lastUser = user;
						ActModel actModel = new ActModel(user);
						listModel.add(actModel);
					}
				}
			} finally {
				log.debug("Last User: " + lastUser.getUserId() + " " + lastUser.getLastnameEn());
			}

			return listModel;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<TaxSystemStatementModel> getTaxSystemStatementModelList(Integer groupId) {
		List<User> users = userService.findUsersByGroup(groupId);
		if (users != null && users.size() > 0) {
			List<TaxSystemStatementModel> listModel = new ArrayList<TaxSystemStatementModel>(users.size());
			for (User user : users) {
				if (user.isActive()) {
					TaxSystemStatementModel taxSystemStatementModel = new TaxSystemStatementModel(user);
					listModel.add(taxSystemStatementModel);
				}
			}
			return listModel;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<IncomeCalculationModel> getIncomeCalculationModelList(Integer groupId) {
		List<User> users = userService.findUsersByGroup(groupId);
		if (users != null && users.size() > 0) {
			List<IncomeCalculationModel> listModel = new ArrayList<IncomeCalculationModel>(users.size());
			for (User user : users) {
				if (user.isActive()) {
					IncomeCalculationModel model = new IncomeCalculationModel(user);
					listModel.add(model);
				}
			}
			return listModel;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Form20OPPModel> getForm20OPPModelList() {
		List<User> users = userService.findActiveUsers();
		if (users != null && users.size() > 0) {
			List<Form20OPPModel> listModel = new ArrayList<Form20OPPModel>(users.size());
			for (User user : users) {
				Form20OPPModel form20OPPModel = new Form20OPPModel(user);
				listModel.add(form20OPPModel);
			}
			return listModel;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Form11KvedModel> getForm11KvedModelList(Integer groupId) {
		List<User> users = userService.findUsersByGroup(groupId);
		if (users != null && users.size() > 0) {
			List<Form11KvedModel> listModel = new ArrayList<Form11KvedModel>(users.size());
			for (User user : users) {
				if (user.isActive()) {
					Form11KvedModel form11Model = new Form11KvedModel(user);
					listModel.add(form11Model);
				}
			}
			return listModel;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> generateDeclarations(Set<Integer> enabledUserIds, Set<Integer> groupIds, Integer year,
			Integer quarter) throws InvalidFormatException, IOException {
		char[] dateYear = ("" + (quarter == 4 ? year + 1 : year)).toCharArray();
		List<String> fileNames = new ArrayList<String>();
		List<Declaration> declarations = declarationService.findDeclarationsByYearAndQuarter(enabledUserIds, year,
				quarter);
		Map<Integer, Declaration> previousDeclarations = new HashMap<Integer, Declaration>();
		if (!quarter.equals(1)) {
			List<Declaration> prevDeclarations = declarationService.findDeclarationsByYearAndQuarter(enabledUserIds,
					year, quarter - 1);
			for (Declaration declaration : prevDeclarations) {
				previousDeclarations.put(declaration.getUser().getUserId(), declaration);
			}
		}

		XlsProcessor xlsProcessor = new XlsProcessor();
		if (declarations != null && declarations.size() > 0) {
			xlsProcessor.cleanBaseDirectory(XlsTemplate.DECLARATION, year, quarter);
		}

		for (Declaration declaration : declarations) {
			User user = declaration.getUser();
			BigDecimal income = declaration.getIncome();
			BigDecimal tax = declaration.getTax();
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("user", user);
			beans.put("year", year);
			beans.put("dateYear", dateYear);
			beans.put("income", income);
			beans.put("tax", tax);
			BigDecimal previousTax = null;
			BigDecimal taxToPay = tax;
			if (!quarter.equals(1) && tax != null) {
				Declaration previousDeclaration = previousDeclarations.get(user.getUserId());
				if (previousDeclaration != null) {
					if (previousDeclaration.getTax() != null) {
						previousTax = previousDeclaration.getTax();
						taxToPay = tax.subtract(previousTax);
					}
				}
			}
			if (previousTax == null) {
				beans.put("previousTax", "-");
			} else {
				beans.put("previousTax", previousTax);
			}
			beans.put("taxToPay", taxToPay);
			beans.put("phone0", "0976884343");
			for (int q = 1; q <= 4; q++) {
				String qsym = q == quarter ? "X" : "";
				beans.put("q" + q, qsym);
			}
			List<Kved2010> kveds = user.getActiveKveds();
			for (int k = 1; k <= 6; k++) {
				String code = "";
				String name = "";
				if (kveds.size() >= k) {
					Kved2010 kved = kveds.get(k - 1);
					code = kved.getCode();
					name = kved.getName();
				}
				beans.put("kvedCode" + k, code);
				beans.put("kvedName" + k, name);
			}
			String outputFilenamePrefix = String.format("/DECLARATION/%d_Q%d/%s_%s_%d_Q%d_", year, quarter,
					user.getLastnameEn(), user.getFirstnameEn(), year, quarter);
			String outputFilename = xlsProcessor.saveReport(XlsTemplate.DECLARATION, outputFilenamePrefix, beans);
			fileNames.add(outputFilename);
		}
		return fileNames;
	}

	@Override
	public List<String> generateActs(Set<Integer> enabledUserIds, Integer year, Integer month,
			boolean generateContracts, boolean generateActs) throws JAXBException, Docx4JException, TemplateException,
			IOException, TransformerException, FOPException {
		List<String> fileNames = new ArrayList<String>();
		List<Act> acts = actService.findActsByYearAndMonth(year, month);
		List<ActModel> listModel = getActModelList(acts, enabledUserIds);
		if (listModel != null) {
			System.out.println("Generated report models: " + listModel.size());
		}
		generateActsDocx(fileNames, listModel, generateContracts, generateActs);
		generateActsPdf(fileNames, listModel, generateContracts, generateActs);

		return fileNames;
	}

	private void generateActsDocx(List<String> fileNames, List<ActModel> listModel, boolean generateContracts,
			boolean generateActs) throws JAXBException, Docx4JException, TemplateException, IOException {
		DocxProcessor docxProcessor = new DocxProcessor();
		if (listModel != null && listModel.size() > 0) {
			docxProcessor.cleanBaseDirectory(DocxTemplate.ACT, listModel.get(0));
		}
		if (generateActs) {
			fileNames.addAll(docxProcessor.saveReports(DocxTemplate.CONTRACT_ANNEX, listModel));
			fileNames.addAll(docxProcessor.saveReports(DocxTemplate.ACT, listModel));
		}
		if (generateContracts) {
			fileNames.addAll(docxProcessor.saveReports(DocxTemplate.CONTRACT, listModel));
		} else {
			List<ActModel> newActModels = new ArrayList<ActModel>();
			for (ActModel actModel : listModel) {
				if (actModel.isNewContract()) {
					newActModels.add(actModel);
				}
			}
			fileNames.addAll(docxProcessor.saveReports(DocxTemplate.CONTRACT, newActModels));
		}
		// docxProcessor.saveReports(DocxTemplate.CONTRACT_ADITIONAL_AGREEMENT,
		// listModel);
	}

	private void generateActsPdf(List<String> fileNames, List<ActModel> listModel, boolean generateContracts,
			boolean generateActs) throws TemplateException, IOException, TransformerException, FOPException {
		FOProcessor foProcessor = new FOProcessor();
		if (generateActs) {
			fileNames.addAll(foProcessor.saveReports(FOTemplate.ACT, listModel, FOType.PDF));
			fileNames.addAll(foProcessor.saveReports(FOTemplate.CONTRACT_ANNEX, listModel, FOType.PDF));
		}
		if (generateContracts) {
			fileNames.addAll(foProcessor.saveReports(FOTemplate.CONTRACT, listModel, FOType.PDF));
		} else {
			List<ActModel> newActModels = new ArrayList<ActModel>();
			for (ActModel actModel : listModel) {
				if (actModel.isNewContract()) {
					newActModels.add(actModel);
				}
			}
			fileNames.addAll(foProcessor.saveReports(FOTemplate.CONTRACT, newActModels, FOType.PDF));
		}
	}

	public List<ActModel> getActModelList(List<Act> acts, Set<Integer> enabledUserIds) {
		List<ActModel> listModel = new ArrayList<ActModel>(acts.size());
		User lastUser = null;
		try {
			for (Act act : acts) {
				lastUser = act.getUser();
				if (enabledUserIds.contains(lastUser.getUserId())) {
					ActModel actModel = new ActModel(act);
					listModel.add(actModel);
				}
			}
		} finally {
			log.error("ActModel error: Last User - " + lastUser.getUserId() + " " + lastUser.getLastnameEn());
		}
		return listModel;
	}

	public List<UserModel> getUserModelList(List<User> users, Date date) {
		List<UserModel> listModel = new ArrayList<UserModel>(users.size());
		for (User user : users) {
			listModel.add(new UserModel(user, date));
		}
		return listModel;
	}

	private static NumberFormat getMoneyFormat() {
		DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
		formatSymbols.setDecimalSeparator(',');
		return new DecimalFormat("#.00", formatSymbols);
	}

	@Override
	public List<String> generateEcpDocuments(Set<Integer> enabledUserIds, Set<Integer> groupIds, Date date)
			throws JAXBException, Docx4JException, TemplateException, IOException, InvalidFormatException {
		List<User> users = userService.findUsersByIds(enabledUserIds);
		if (users == null || users.size() == 0) {
			return Collections.emptyList();
		}

		XlsProcessor xlsProcessor = new XlsProcessor();
		xlsProcessor.cleanBaseDirectory(XlsTemplate.ECP_REGISTRATION);

		List<String> fileNames = new ArrayList<String>();
		Map<String, Object> beans = new HashMap<String, Object>();
		if (date != null) {
			beans.put("dateDay", DAY_FORMAT.format(date));
			beans.put("dateMonth", UA_MONTH_FORMAT.format(date));
			beans.put("dateYear", YEAR_FORMAT.format(date));
		} else {
			beans.put("dateDay", "   ");
			beans.put("dateMonth", "_______________");
			beans.put("dateYear", YEAR_FORMAT.format(new Date()));
		}
		for (User user : users) {
			beans.put("user", user);
			String outputFilenamePrefix = String.format("/ECP/%s_%s_", user.getLastnameEn(), user.getFirstnameEn());
			String outputFilename = xlsProcessor.saveReport(XlsTemplate.ECP_REGISTRATION, outputFilenamePrefix, beans);
			fileNames.add(outputFilename);
		}
		return fileNames;

		// List<User> ecpUsers = userService.findUsersByIds(enabledUserIds);
		// List<UserModel> userModelList = getUserModelList(ecpUsers, date);
		// DocxProcessor docxProcessor = new DocxProcessor();
		//
		// if (userModelList != null && userModelList.size() > 0) {
		// docxProcessor.cleanBaseDirectory(DocxTemplate.ECP_REGISTRATION,
		// userModelList.get(0));
		// }
		//
		// List<String> fileNames = new ArrayList<String>();
		// fileNames.addAll(docxProcessor.saveReports(DocxTemplate.ECP_REGISTRATION,
		// userModelList));
		// fileNames.addAll(docxProcessor.saveReports(DocxTemplate.ECP_JOIN,
		// userModelList));
		// return fileNames;
	}

	@Override
	public List<String> generatePaymentDocuments(Set<Integer> enabledUserIds, boolean sendEmail)
			throws InvalidFormatException, IOException {

		Calendar calendar = Calendar.getInstance(Locale.US);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		if (month == 0) {
			year = year - 1;
			month = 12;
		}

		int quarter = month / 3;
		String rentPeriod = StrUtil.formatUAMonth(month) + " " + year;
		String esvPeriod = rentPeriod;
		String taxPeriod = quarter + " кв. " + year;

		calendar.set(Calendar.DAY_OF_MONTH, 19);
		Date endDate = calendar.getTime();

		boolean showTaxPayment = month % 3 == 0;

		int i = 0;
		List<User> users = userService.findUsersByIds(enabledUserIds);
		Map<Integer, Declaration> userDeclarations = new HashMap<Integer, Declaration>();
		Map<Integer, Declaration> previousDeclarations = new HashMap<Integer, Declaration>();
		if (showTaxPayment) {
			List<Declaration> declarations = declarationService.findDeclarationsByYearAndQuarter(enabledUserIds, year,
					quarter);
			for (Declaration declaration : declarations) {
				userDeclarations.put(declaration.getUser().getUserId(), declaration);
			}
			if (quarter != 1) {
				List<Declaration> prevDeclarations = declarationService.findDeclarationsByYearAndQuarter(
						enabledUserIds, year, quarter - 1);
				for (Declaration declaration : prevDeclarations) {
					previousDeclarations.put(declaration.getUser().getUserId(), declaration);
				}
			}
		}

		List<String> fileNames = new ArrayList<String>();
		XlsProcessor xlsProcessor = new XlsProcessor();
		if (users != null && users.size() > 0) {
			xlsProcessor.cleanBaseDirectory(XlsTemplate.PAYMENTS, year, month);
		}
		TextProcessor textProcessor = sendEmail ? new TextProcessor(TextTemplate.PAYMENT_DETAILS_EMAIL) : null;
		if (users != null) {
			for (User user : users) {
				if (user.isActive()) {

					boolean showRentPayment = false;
					Map<String, Object> beans = new HashMap<String, Object>();
					if (user.getRentType() != null && !user.getRentType().equals(RentType.NONE)) {
						showRentPayment = true;
						String rentDetails = user.getRentType().getDescription();
						int rentAmount = user.getRentType().getAmount();
						String rentPDV = getMoneyFormat().format((double) rentAmount / 6);
						beans.put("rentPeriod", rentPeriod);
						beans.put("rentAmount", rentAmount);
						beans.put("rentDetails", rentDetails);
						beans.put("rentPDV", rentPDV);
						beans.put("rentContractDate", UA_DATE_FORMAT.format(user.getRentContractDate()));
					}
					if (showTaxPayment) {
						Declaration userDeclaration = userDeclarations.get(user.getUserId());
						BigDecimal taxToPay = userDeclaration == null ? null : userDeclaration.getTax();
						BigDecimal previousTax = null;
						if (quarter != 1 && taxToPay != null) {
							Declaration previousDeclaration = previousDeclarations.get(user.getUserId());
							if (previousDeclaration != null) {
								if (previousDeclaration.getTax() != null) {
									previousTax = previousDeclaration.getTax();
									taxToPay = taxToPay.subtract(previousTax);
								}
							}
						}
						beans.put("taxAmount", taxToPay);
						beans.put("taxPeriod", taxPeriod);
					}
					beans.put("user", user);
					beans.put("showTaxPayment", showTaxPayment);
					beans.put("showRentPayment", showRentPayment);
					beans.put("esvPeriod", esvPeriod);
					String outputFilenamePrefix = String.format("/Payments/%d_%02d/%s_%s_%d_%02d_", year, month,
							user.getLastnameEn(), user.getFirstnameEn(), year, month);
					String outputFilename = xlsProcessor.saveReport(XlsTemplate.PAYMENTS, outputFilenamePrefix, beans);
					fileNames.add(outputFilename);
					i++;
					if (sendEmail) {
						beans.put("endDate", DAY_MONTH_EN_FORMAT.format(endDate));
						sendEmail(textProcessor, "Payment details", beans, new File(XlsProcessor.REPORTS_DIRECTORY
								+ outputFilename));
					}
				}
			}
		}
		System.out.println("Generated user Payment files: " + i);
		return fileNames;
	}

	private void sendEmail(TextProcessor textProcessor, String subject, Map<String, Object> beans, File attachmentFile) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(new InternetAddress(adminEmail));
			helper.setSubject(subject);
			helper.setText(textProcessor.processTemplateText(beans));
			FileSystemResource attachment = new FileSystemResource(attachmentFile);
			helper.addAttachment(attachment.getFilename(), attachment);
			mailSender.send(mimeMessage);
		} catch (Exception ex) {
			log.error("Send email failed.", ex);
		}
	}

}