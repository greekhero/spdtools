package ua.org.tumakha.util;

/**
 * @author Yuriy Tumakha
 */
public abstract class NumberUtilUa {

    public final static int DG_POWER = 6;

    // private final static String[][] a_power = new String[][] {
    // { "0", "", "", "" }, // 1
    // { "1", "тысяча ", "тысячи ", "тысяч " }, // 2
    // { "0", "миллион ", "миллиона ", "миллионов " }, // 3
    // { "0", "миллиард ", "миллиарда ", "миллиардов " }, // 4
    // { "0", "триллион ", "триллиона ", "триллионов " }, // 5
    // { "0", "квадриллион ", "квадриллиона ", "квадриллионов " }, // 6
    // { "0", "квинтиллион ", "квинтиллиона ", "квинтиллионов " } // 7
    // };

    // private final static String[][] digit = new String[][] {
    // { "", "", "десять ", "", "" },
    // { "один ", "одна ", "одиннадцать ", "десять ", "сто " },
    // { "два ", "две ", "двенадцать ", "двадцать ", "двести " },
    // { "три ", "три ", "тринадцать ", "тридцать ", "триста " },
    // { "четыре ", "четыре ", "четырнадцать ", "сорок ", "четыреста " },
    // { "пять ", "пять ", "пятнадцать ", "пятьдесят ", "пятьсот " },
    // { "шесть ", "шесть ", "шестнадцать ", "шестьдесят ", "шестьсот " },
    // { "семь ", "семь ", "семнадцать ", "семьдесят ", "семьсот " },
    // { "восемь ", "восемь ", "восемнадцать ", "восемьдесят ",
    // "восемьсот " },
    // { "девять ", "девять ", "девятнадцать ", "девяносто ", "девятьсот " } };

    private final static String[][] a_power = new String[][] { { "0", "", "", "" }, // 1
            { "1", "тисяча ", "тисячі ", "тисяч " }, // 2
            { "0", "мільйон ", "мільйони ", "мільйонів " }, // 3
            { "0", "мільярд ", "мільярди ", "мільярдів " }, // 4
            { "0", "трильйон ", "трильйона ", "трильйонів " }, // 5
            { "0", "квадрильйон", "квадрильйона", "квадрильйонів" }, // 6
            { "0", "квінтильйон", "квінтильйона", "квінтильйонів" } // 7
    };

    private final static String[][] digit = new String[][] { { "", "", "десять ", "", "" },
            { "один ", "одна ", "одинадцять ", "десять ", "сто " },
            { "два ", "дві ", "дванадцять ", "двадцять ", "двісті " },
            { "три ", "три ", "тринадцять ", "тридцять ", "триста " },
            { "чотири ", "чотири ", "чотирнадцять ", "сорок ", "чотириста " },
            { "п'ять ", "п'ять ", "п'ятнадцять ", "п'ятдесят ", "п'ятсот " },
            { "шість ", "шість ", "шіснадцять ", "шістдесят ", "шістсот " },
            { "сім ", "сім ", "сімнадцать ", "сімдесят ", "сімсот " },
            { "вісім ", "вісім ", "вісімнадцать ", "вісімдесят ", "вісімсот " },
            { "дев'ять ", "дев'ять ", "дев'ятнадцать ", "дев'яносто ", "дев'ятсот " } };

    private final static String[] DOLLARS = new String[] { "долар", "долари", "доларів" };

    public static String toString(int sum) {
        int i, mny;
        StringBuffer result = new StringBuffer("");
        long divisor;
        int psum = sum;

        int one = 1;
        int four = 2;
        int many = 3;

        int hun = 4;
        int dec = 3;
        int dec2 = 2;

        if (sum == 0)
            return "нуль ";
        if (sum < 0) {
            result.append("мінус ");
            psum = -psum;
        }

        for (i = 0, divisor = 1; i < DG_POWER; i++)
            divisor *= 1000;

        for (i = DG_POWER - 1; i >= 0; i--) {
            divisor /= 1000;
            mny = (int) (psum / divisor);
            psum %= divisor;
            // str="";
            if (mny == 0) {
                if (i > 0)
                    continue;
                result.append(a_power[i][one]);
            } else {
                if (mny >= 100) {
                    result.append(digit[mny / 100][hun]);
                    mny %= 100;
                }
                if (mny >= 20) {
                    result.append(digit[mny / 10][dec]);
                    mny %= 10;
                }
                if (mny >= 10) {
                    result.append(digit[mny - 10][dec2]);
                } else {
                    if (mny >= 1)
                        result.append(digit[mny]["0".equals(a_power[i][0]) ? 0 : 1]);
                }
                switch (mny) {
                case 1:
                    result.append(a_power[i][one]);
                    break;
                case 2:
                case 3:
                case 4:
                    result.append(a_power[i][four]);
                    break;
                default:
                    result.append(a_power[i][many]);
                    break;
                }
                ;
            }
        }
        return result.toString();
    }

    // public static String toString(double num) {
    // return toString((int) num) + "."
    // + toString((int) (num * 100 - ((int) num) * 100));
    // }

    public static String numberInWords(long number) {
        return toString((int) number).trim();
    }

    public static String numberInDollars(long number) {
        String dollars;
        number = Math.abs(number);
        int one = 0;
        int four = 1;
        int many = 2;
        int dec = (int) number % 100;
        if (dec >= 11 && dec <= 14) {
            number = 15;
        }
        int mny = (int) number % 10;
        switch (mny) {
        case 1:
            dollars = DOLLARS[one];
            break;
        case 2:
        case 3:
        case 4:
            dollars = DOLLARS[four];
            break;
        default:
            dollars = DOLLARS[many];
            break;
        }
        ;
        return dollars;
    }
}
