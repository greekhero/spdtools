package ua.org.tumakha.spdtool.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Yuriy Tumakha
 */
public class PassportTest {

    private static final String PASSPORT_NUMBER = "012233";

    private Passport passport;

    @Before
    public void init() {
        passport = new Passport();
    }

    @Test
    public void testPassportEntity() {
        passport.setNumber(PASSPORT_NUMBER);
        assertEquals("Passport numbers not equals", PASSPORT_NUMBER, passport.getNumber());
        assertTrue(passport.getTextUa().contains(PASSPORT_NUMBER));
    }

}
