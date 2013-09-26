package ua.org.tumakha.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yuriy Tumakha
 */
public abstract class PaymentPurposeUtil {

    private static int SIMPLE_PURPOSE_MAX_LENGTH = 30;
    private static Pattern ALL_NUMBERS_PATTERN = Pattern.compile("\\d+");
    private static Pattern ALL_MONEYS_PATTERN = Pattern.compile("\\d+\\.\\d+");
    private static Pattern USD_AMOUNT_PATTERN = Pattern.compile("\\d+\\.\\d+ USD");
    private static Pattern SELL_DATE_PATTERN = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");

    public static String simplifyIncomePaymentPurpose(String transPurpose) {
        if (transPurpose.contains("продаж") && transPurpose.contains("USD")) {
            Matcher matcher = ALL_MONEYS_PATTERN.matcher(transPurpose);
            Double amount = null;
            Double rate = null;
            while (matcher.find()) {
                String money = matcher.group();
                if (amount == null) {
                    amount = Double.parseDouble(money);
                } else {
                    rate = Double.parseDouble(money);
                    break;
                }
            }
            return String.format("прод. %s USD к. %s", getMoneyFormat().format(amount), getRateFormat().format(rate));
        } else if (transPurpose.length() > SIMPLE_PURPOSE_MAX_LENGTH) {
            return "Додатковий дохід";
        } else {
            return transPurpose;
        }
    }

    public static Integer getIncomeCommissionUSD(String transPurpose) {
        if (transPurpose.contains("продаж") && transPurpose.contains("USD")) {
            Matcher matcher = ALL_MONEYS_PATTERN.matcher(transPurpose);
            String money = "0";
            while (matcher.find()) {
                money = matcher.group();
            }
            return Integer.parseInt(money.replace(".", ""));
        } else {
            return null;
        }
    }

    public static String getIncomeDateAndAmountUSD(String transPurpose) {
        if (transPurpose.contains("продаж") && transPurpose.contains("USD")) {
            Matcher matcher = USD_AMOUNT_PATTERN.matcher(transPurpose);
            String usdAmount = matcher.find() ? matcher.group() : null;
            matcher = SELL_DATE_PATTERN.matcher(transPurpose);
            String date = matcher.find() ? matcher.group() : null;
            return date + " " + usdAmount;
        } else {
            return "";
        }
    }

    public static String getDateAmountKey(Date date, Double amount) {
        return String.format("%1$td/%1$tm/%1$tY %2$.2f USD", date, amount).replace(",", ".");
    }

    public static String simplifyExpensePaymentPurpose(String transPurpose) {
        if (transPurpose.startsWith("Комісія банку за ведення рахунків")) {
            return "б.о.";
        } else if (transPurpose.startsWith("Оплата за оренду")) {
            return "Оренда";
        } else if (transPurpose.contains("ЄСВ")) {
            return "ЄСВ";
        } else if (transPurpose.contains("диний податок") || transPurpose.contains("диный налог")) {
            String year = "";
            String quarter = "";
            Matcher matcher = ALL_NUMBERS_PATTERN.matcher(transPurpose);
            String previous = "";
            while (matcher.find()) {
                String number = matcher.group();
                if (number.length() == 4 && number.startsWith("20")) {
                    year = number;
                    if (previous.equals(quarter)) {
                        break;
                    }
                }
                if (number.length() == 1 && Integer.parseInt(number) < 5) {
                    quarter = number;
                    if (previous.equals(year)) {
                        break;
                    }
                }
                previous = number;
            }
            return String.format("ЄП за %s кв. %s", quarter, year);
        } else if (transPurpose.length() > SIMPLE_PURPOSE_MAX_LENGTH) {
            return "особисті витрати";

        } else {
            return transPurpose;
        }
    }

    private static NumberFormat getMoneyFormat() {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setDecimalSeparator(',');
        return new DecimalFormat("#.00", formatSymbols);
    }

    private static NumberFormat getRateFormat() {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setDecimalSeparator(',');
        return new DecimalFormat("#.0###", formatSymbols);
    }
}
