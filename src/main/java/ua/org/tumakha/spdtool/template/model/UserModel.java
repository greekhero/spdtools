package ua.org.tumakha.spdtool.template.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.template.DocxTemplate;
import ua.org.tumakha.util.StrUtil;

/**
 * @author Yuriy Tumakha
 */
public class UserModel extends TemplateModel {

	private static final DateFormat DAY_FORMAT = new SimpleDateFormat("dd");
	private static final DateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
	private static final DateFormat UA_MONTH_FORMAT = new SimpleDateFormat("MMMMM", uaLocale);
	private static final DateFormat MONTH_DIGIT_FORMAT = new SimpleDateFormat("MM", uaLocale);

	private User user;

	private Date date;

	private String dateDay;

	private String dateMonth;

	private String dateMonthDigit;

	private String dateYear;

	public UserModel() {
	}

	public UserModel(User user) {
		super(user);
		this.user = user;
	}

	public UserModel(User user, Date date) {
		this(user);
		setDate(date);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		if (date != null) {
			dateDay = DAY_FORMAT.format(date);
			dateMonth = StrUtil.padRight(UA_MONTH_FORMAT.format(date), 15);
			dateMonthDigit = StrUtil.padRight(MONTH_DIGIT_FORMAT.format(date), 2);
			dateYear = YEAR_FORMAT.format(date);
		} else {
			dateYear = YEAR_FORMAT.format(new Date());
		}
		this.date = date;
	}

	@Override
	public String getPhone() {
		String phone = StringUtils.isEmpty(user.getPhone()) ? "0958912127" : user.getPhone();
		return StrUtil.padRight(phone, 12);
	}

	public String getName() {
		StringBuffer name = new StringBuffer();
		name.append(user.getLastname());
		name.append(" ");
		name.append(user.getFirstname());
		name.append(" ");
		name.append(user.getMiddlename());
		return StrUtil.padRight(name.toString(), 120);
	}

	public String getDateDay() {
		return dateDay == null ? "  " : dateDay;
	}

	public String getDateMonth() {
		return dateMonth == null ? "_____________" : dateMonth;
	}

	public String getDateMonthDigit() {
		return dateMonthDigit == null ? "  " : dateMonthDigit;
	}

	public String getDateYear() {
		return dateYear == null ? "    " : dateYear;
	}

	public void setDateDay(String dateDay) {
		this.dateDay = dateDay;
	}

	public void setDateMonth(String dateMonth) {
		this.dateMonth = dateMonth;
	}

	public void setDateMonthDigit(String dateMonthDigit) {
		this.dateMonthDigit = dateMonthDigit;
	}

	public void setDateYear(String dateYear) {
		this.dateYear = dateYear;
	}

	@Override
	public String getOutputFilename(DocxTemplate template) {
		return String.format("/ECP/%s/%s_%s_%s", getLastname(), getLastnameEn(), getFirstnameEn(),
				template.getFilename());

	}

}
