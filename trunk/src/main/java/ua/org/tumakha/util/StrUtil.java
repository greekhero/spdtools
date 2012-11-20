package ua.org.tumakha.util;

import org.springframework.util.StringUtils;

/**
 * @author Yuriy Tumakha
 */
public abstract class StrUtil {

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

	public static String padRight(String str, int num) {
		str = str.replace("-", "_");
		str = String.format("%1$-" + num + "s", str);
		return str.replace("_", "-");
	}

}
