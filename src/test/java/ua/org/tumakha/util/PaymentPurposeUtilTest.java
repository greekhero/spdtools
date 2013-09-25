package ua.org.tumakha.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Yuriy Tumakha
 */
public class PaymentPurposeUtilTest {

    private static String UNKNOWN_PURPOSE = "Оплата за консультаційні послуги, рах. 1 від 22.01.13, Без ПДВ.";
    private static String SIMPLE_PURPOSE = "Оренда";

    @Test
    public void testSimplifyIncomePaymentPurpose() {
        assertEquals("Додатковий дохід", PaymentPurposeUtil.simplifyIncomePaymentPurpose(UNKNOWN_PURPOSE));
        assertEquals(SIMPLE_PURPOSE, PaymentPurposeUtil.simplifyIncomePaymentPurpose(SIMPLE_PURPOSE));
        assertEquals("прод. 1000,20 USD к. 8,137", PaymentPurposeUtil.simplifyIncomePaymentPurpose("Кошти від продажу 1000.20 USD згідно заяви № 5 від 15/01/2013 курс 8.137000000000 Комісія 20.19"));
        assertEquals(new Integer(2019), PaymentPurposeUtil.getIncomeCommissionUSD("Кошти від продажу 1000.20 USD згідно заяви № 5 від 15/01/2013 курс 8.137000000000 Комісія 20.19"));
    }

    @Test
    public void testSimplifyExpensePaymentPurpose() {
        assertEquals("особисті витрати", PaymentPurposeUtil.simplifyExpensePaymentPurpose(UNKNOWN_PURPOSE));
        assertEquals(SIMPLE_PURPOSE, PaymentPurposeUtil.simplifyExpensePaymentPurpose(SIMPLE_PURPOSE));
        assertEquals("б.о.", PaymentPurposeUtil.simplifyExpensePaymentPurpose("Комісія банку за ведення рахунків ..."));
        assertEquals("Оренда", PaymentPurposeUtil.simplifyExpensePaymentPurpose("Оплата за оренду приміщення та офісного обладнання ..."));
        assertEquals("ЄСВ", PaymentPurposeUtil.simplifyExpensePaymentPurpose("1234567890; ЄСВ 34,7% Реєстр.№ 123456789 ФОП ..."));
        assertEquals("ЄП за 1 кв. 2013", PaymentPurposeUtil.simplifyExpensePaymentPurpose("*;101;1234567890;Єдиний податок за 1 кв. 2013 року 3 група 5% ФОП ..."));
        assertEquals("ЄП за 1 кв. 2013", PaymentPurposeUtil.simplifyExpensePaymentPurpose("*;101;1234567890;Єдиний податок за 1 квартал 2013 року 3 група 5% ФОП ..."));
        assertEquals("ЄП за 1 кв. 2013", PaymentPurposeUtil.simplifyExpensePaymentPurpose("*;101;1234567890;Єдиний податок 1кв.2013р. 3 група 5% ФОП ..."));
        assertEquals("ЄП за 1 кв. 2013", PaymentPurposeUtil.simplifyExpensePaymentPurpose("*;101;1234567890;Єдиний податок за 2013 р. 1 кв. 3 група 5% ФОП ..."));
    }
}
