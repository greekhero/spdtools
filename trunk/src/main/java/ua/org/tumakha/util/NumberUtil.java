package ua.org.tumakha.util;

/**
 * @author Yuriy Tumakha
 */
public abstract class NumberUtil {

    public static String numberInWordsEn(long number) {
        return NumberUtilEn.numberInWords(number);
    }

    public static String numberInWordsUa(long number) {
        return NumberUtilUa.numberInWords(number);
    }

    public static String numberInDollarsUa(long number) {
        return NumberUtilUa.numberInDollars(number);
    }

}
