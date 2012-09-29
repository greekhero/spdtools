package ua.org.tumakha.spdtool.template.model;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ua.org.tumakha.spdtool.entity.Act;
import ua.org.tumakha.spdtool.entity.Contract;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.template.DocxTemplate;
import ua.org.tumakha.spdtool.template.FOTemplate;
import ua.org.tumakha.util.NumberUtil;

/**
 * @author Yuriy Tumakha
 */
public class ActModel extends TemplateModel {

	private static final DateFormat yearMonthFormat = new SimpleDateFormat(
			"yyyy-MM");
	private static final DateFormat contractDateFormat = new SimpleDateFormat(
			"dd MMMMM yyyy", uaLocale);
	private static final DateFormat contractDateFormatEn = new SimpleDateFormat(
			"dd MMMMM yyyy", enLocale);
	private static final DateFormat actDateFormat = new SimpleDateFormat(
			"«dd»  MMMMM  yyyy р", uaLocale);
	private static final DateFormat actDateFormatEn = new SimpleDateFormat(
			"«dd»  MMMMM  yyyy", enLocale);
	private static final DateFormat actPeriodFormat = new SimpleDateFormat(
			"dd MMMMM yyyy", uaLocale);
	private static final DateFormat actPeriodFormatEn = new SimpleDateFormat(
			"dd 'of' MMMMM yyyy", enLocale);
	private static final NumberFormat amountFormat = NumberFormat
			.getNumberInstance(enLocale);

	private boolean newContract;
	private String actNo;
	private String contractNo;
	private String contractDate;
	private String contractDateEn;
	private String serviceType;
	private String serviceTypeVOrudnomu;
	private String serviceTypeEn;
	private String date;
	private String dateEn;
	private String startDate;
	private String startDateEn;
	private String dateFrom;
	private String dateFromEn;
	private String dateFromDigit;
	private String dateTo;
	private String dateToEn;
	private String dateToDigit;
	private String amountDigit;
	private String amountUa;
	private String amountEn;

	public ActModel() {
	}

	public ActModel(User user) {
		super(user);
		copyProperties(user, user.getLastAct());
	}

	public ActModel(Act act) {
		super(act.getUser());
		copyProperties(act.getUser(), act);
	}

	private void copyProperties(User user, Act act) {
		newContract = user.getLastContract().getDate().getTime() + 2160000000L > act
				.getDateFrom().getTime();
		serviceType = user.getServiceType().getName();
		serviceTypeVOrudnomu = user.getServiceType().getNameVOrudnomu();
		serviceTypeEn = user.getServiceType().getNameEn();
		if (act != null) {
			Contract contract = act.getContract();
			actNo = act.getNumber();
			contractNo = contract.getNumber();
			contractDate = contractDateFormat.format(contract.getDate());
			contractDateEn = contractDateFormatEn.format(contract.getDate());
			date = actDateFormat.format(act.getDateTo());
			dateEn = actDateFormatEn.format(act.getDateTo());
			startDate = actDateFormat.format(act.getDateFrom());
			startDateEn = actDateFormatEn.format(act.getDateFrom());
			dateFrom = actPeriodFormat.format(act.getDateFrom());
			dateFromEn = actPeriodFormatEn.format(act.getDateFrom());
			dateFromDigit = uaDateFormat.format(act.getDateFrom());
			dateTo = actPeriodFormat.format(act.getDateTo());
			dateToEn = actPeriodFormatEn.format(act.getDateTo());
			dateToDigit = uaDateFormat.format(act.getDateTo());
			int amount = act.getAmount().intValue();
			amountDigit = amountFormat.format(amount);
			amountUa = NumberUtil.numberInWordsUa(amount) + " "
					+ NumberUtil.numberInDollarsUa(amount);
			amountEn = NumberUtil.numberInWordsEn(amount);
		}
	}

	public boolean isNewContract() {
		return newContract;
	}

	public void setNewContract(boolean newContract) {
		this.newContract = newContract;
	}

	public String getN() {
		return "" + Integer.parseInt(actNo.substring(actNo.length() - 2));
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getContractDateEn() {
		return contractDateEn;
	}

	public void setContractDateEn(String contractDateEn) {
		this.contractDateEn = contractDateEn;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceTypeVOrudnomu() {
		return serviceTypeVOrudnomu;
	}

	public void setServiceTypeVOrudnomu(String serviceTypeVOrudnomu) {
		this.serviceTypeVOrudnomu = serviceTypeVOrudnomu;
	}

	public String getServiceTypeEn() {
		return serviceTypeEn;
	}

	public void setServiceTypeEn(String serviceTypeEn) {
		this.serviceTypeEn = serviceTypeEn;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDateEn() {
		return dateEn;
	}

	public void setDateEn(String dateEn) {
		this.dateEn = dateEn;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDateEn() {
		return startDateEn;
	}

	public void setStartDateEn(String startDateEn) {
		this.startDateEn = startDateEn;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateFromEn() {
		return dateFromEn;
	}

	public void setDateFromEn(String dateFromEn) {
		this.dateFromEn = dateFromEn;
	}

	public String getDateFromDigit() {
		return dateFromDigit;
	}

	public void setDateFromDigit(String dateFromDigit) {
		this.dateFromDigit = dateFromDigit;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getDateToEn() {
		return dateToEn;
	}

	public void setDateToEn(String dateToEn) {
		this.dateToEn = dateToEn;
	}

	public String getDateToDigit() {
		return dateToDigit;
	}

	public void setDateToDigit(String dateToDigit) {
		this.dateToDigit = dateToDigit;
	}

	public String getAmountDigit() {
		return amountDigit;
	}

	public void setAmountDigit(String amountDigit) {
		this.amountDigit = amountDigit;
	}

	public String getAmountUa() {
		return amountUa;
	}

	public void setAmountUa(String amountUa) {
		this.amountUa = amountUa;
	}

	public String getAmountEn() {
		return amountEn;
	}

	public void setAmountEn(String amountEn) {
		this.amountEn = amountEn;
	}

	@Override
	public String getOutputFilename(DocxTemplate template) {
		String month = "";
		try {
			if (dateToEn != null) {
				month = yearMonthFormat.format(actPeriodFormatEn
						.parse(dateToEn));
			}
		} catch (ParseException e) {
			throw new IllegalStateException(e);
		}
		return String.format("/%s/%s/ICGU_%s_%s_%s", month, getLastname(),
				getFirstnameEn().substring(0, 1) + getMiddlenameEn().charAt(0)
						+ getLastnameEn().charAt(0), month.replace("-", "_"),
				template.getFilename());

	}

	@Override
	public String getOutputFilename(FOTemplate template) {
		String month = "";
		try {
			if (dateToEn != null) {
				month = yearMonthFormat.format(actPeriodFormatEn
						.parse(dateToEn));
			}
		} catch (ParseException e) {
			throw new IllegalStateException(e);
		}
		return String.format("/%s/%s_%s_%s_%s", month, getLastnameEn(),
				getFirstnameEn().substring(0, 1) + getMiddlenameEn().charAt(0)
						+ getLastnameEn().charAt(0), month.replace("-", "_"),
				template.getFilename());

	}

}
