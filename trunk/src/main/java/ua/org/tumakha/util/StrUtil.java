package ua.org.tumakha.util;

import org.springframework.util.StringUtils;

/**
 * @author Yuriy Tumakha
 */
public abstract class StrUtil {

    public static boolean isFirstCharUpper(String str) {
        if (!StringUtils.hasText(str)) {
            return false;
        }
        return Character.isUpperCase(str.charAt(0));
    }

}
