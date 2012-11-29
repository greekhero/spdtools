package ua.org.tumakha.spdtool.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import ua.org.tumakha.util.StrUtil;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "passport")
public class Passport implements Serializable {

	private static final long serialVersionUID = 1308052916443329074L;

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd.MM.yyyy");
	private static final DateFormat DAY_FORMAT = new SimpleDateFormat("dd");
	private static final DateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
	private static final DateFormat MONTH_DIGIT_FORMAT = new SimpleDateFormat(
			"MM");

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer passportId;

	@OneToOne
	@JoinColumn(name = "userId", unique = true, nullable = false, updatable = false)
	private User user;

	@Size(max = 2)
	@Column(length = 2)
	private String seria;

	@Digits(integer = 6, fraction = 0)
	private Integer number;

	@Size(max = 100)
	@Column(length = 100)
	private String organ;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	@Past
	private Date date;

	public String getSeriaStr() {
		return StrUtil.padRight(seria, 2);
	}

	public String getNumberStr() {
		if (number != null) {
			return String.format("%06d", number);
		} else {
			return "      ";
		}
	}

	public String getOrganStr() {
		return StrUtil.padRight(organ, 140);
	}

	public String getDateDay() {
		if (date == null) {
			return "  ";
		} else {
			return DAY_FORMAT.format(date);
		}
	}

	public String getDateMonthDigit() {
		if (date == null) {
			return "  ";
		} else {
			return MONTH_DIGIT_FORMAT.format(date);
		}

	}

	public String getDateYear() {
		if (date == null) {
			return "    ";
		} else {
			return YEAR_FORMAT.format(date);
		}
	}

	public String getTextUa() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(seria);
		buffer.append(" ");
		buffer.append(number == null ? "" : number);
		buffer.append(", ");
		buffer.append(organ);
		if (date != null) {
			buffer.append(", ");
			buffer.append(DATE_FORMAT.format(date));
		}
		return buffer.toString().trim();
	}

	public Integer getPassportId() {
		return passportId;
	}

	public void setPassportId(Integer passportId) {
		this.passportId = passportId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSeria() {
		return seria;
	}

	public void setSeria(String seria) {
		this.seria = seria;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
