package ua.org.tumakha.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Yuriy Tumakha
 */
public class NumberUtilUaTest {

    @Test
    public void testNumberInDollars() {
        assertEquals("доларів", NumberUtilUa.numberInDollars(0));
        assertEquals("долар", NumberUtilUa.numberInDollars(-1));
        assertEquals("долар", NumberUtilUa.numberInDollars(1));
        assertEquals("долари", NumberUtilUa.numberInDollars(2));
        assertEquals("долари", NumberUtilUa.numberInDollars(3));
        assertEquals("долари", NumberUtilUa.numberInDollars(4));
        assertEquals("доларів", NumberUtilUa.numberInDollars(5));
        assertEquals("доларів", NumberUtilUa.numberInDollars(6));
        assertEquals("доларів", NumberUtilUa.numberInDollars(7));
        assertEquals("доларів", NumberUtilUa.numberInDollars(8));
        assertEquals("доларів", NumberUtilUa.numberInDollars(9));
        assertEquals("доларів", NumberUtilUa.numberInDollars(10));
        assertEquals("доларів", NumberUtilUa.numberInDollars(11));
        assertEquals("доларів", NumberUtilUa.numberInDollars(12));
        assertEquals("доларів", NumberUtilUa.numberInDollars(13));
        assertEquals("доларів", NumberUtilUa.numberInDollars(14));
        assertEquals("доларів", NumberUtilUa.numberInDollars(15));
        assertEquals("доларів", NumberUtilUa.numberInDollars(16));
        assertEquals("доларів", NumberUtilUa.numberInDollars(17));
        assertEquals("доларів", NumberUtilUa.numberInDollars(18));
        assertEquals("доларів", NumberUtilUa.numberInDollars(19));
        assertEquals("доларів", NumberUtilUa.numberInDollars(20));
        assertEquals("долар", NumberUtilUa.numberInDollars(21));
        assertEquals("долари", NumberUtilUa.numberInDollars(22));
        assertEquals("долари", NumberUtilUa.numberInDollars(23));
        assertEquals("долари", NumberUtilUa.numberInDollars(24));
        assertEquals("доларів", NumberUtilUa.numberInDollars(25));
        assertEquals("доларів", NumberUtilUa.numberInDollars(26));
        assertEquals("доларів", NumberUtilUa.numberInDollars(27));
        assertEquals("доларів", NumberUtilUa.numberInDollars(28));
        assertEquals("доларів", NumberUtilUa.numberInDollars(29));
        assertEquals("доларів", NumberUtilUa.numberInDollars(30));
        assertEquals("долар", NumberUtilUa.numberInDollars(31));
        assertEquals("долари", NumberUtilUa.numberInDollars(32));
        assertEquals("долари", NumberUtilUa.numberInDollars(33));
        assertEquals("долари", NumberUtilUa.numberInDollars(34));
        assertEquals("доларів", NumberUtilUa.numberInDollars(35));
        assertEquals("доларів", NumberUtilUa.numberInDollars(36));
        assertEquals("доларів", NumberUtilUa.numberInDollars(100));
        assertEquals("доларів", NumberUtilUa.numberInDollars(600));
        assertEquals("доларів", NumberUtilUa.numberInDollars(175));
        assertEquals("доларів", NumberUtilUa.numberInDollars(1000));
        assertEquals("долар", NumberUtilUa.numberInDollars(1231));
        assertEquals("долари", NumberUtilUa.numberInDollars(4762));
    }

}
