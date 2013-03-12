package ua.org.tumakha.util;

import java.text.DateFormatSymbols;
import java.util.Locale;

import org.springframework.util.StringUtils;

/**
 * @author Yuriy Tumakha
 */
public abstract class StrUtil {

	private final static String[] MONTHS = new String[] { "січень", "лютий", "березень", "квітень", "травень",
			"червень", "липень", "серпень", "вересень", "жовтень", "листопад", "грудень" };

	public static boolean isFirstCharUpperOrDigit(String str) {
		if (!StringUtils.hasText(str)) {
			return false;
		}
		char ch = str.charAt(0);
		return Character.isUpperCase(ch) || Character.isDigit(ch);
	}

	public static boolean isFirstCharUpper(String str) {
		if (!StringUtils.hasText(str)) {
			return false;
		}
		return Character.isUpperCase(str.charAt(0));
	}

	public static String charAt(String str, int index) {
		String result = "";
		if (str != null && str.length() > index) {
			result += str.charAt(index);
		}
		return result;
	}

	public static String padRight(String str, int width) {
		if (str == null) {
			str = "";
		}
		return org.apache.commons.lang.StringUtils.rightPad(str, width);
	}

	private static String formatMonth(int month, Locale locale) {
		if (month >= 1 && month <= 12) {
			return new DateFormatSymbols(locale).getMonths()[month - 1];
		} else {
			return null;
		}
	}

	public static String formatUAMonth(int month) {
		if (month >= 1 && month <= 12) {
			return MONTHS[month - 1];
		} else {
			return null;
		}
	}

	public static String formatUSMonth(int month) {
		return formatMonth(month, Locale.US);
	}

}
