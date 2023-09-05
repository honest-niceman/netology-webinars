package com.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RussianPhoneValidatorTest {
    static String[] validPhones;

    @BeforeAll
    public static void setUp() {
        validPhones = new String[] {"+79967467999", "+79967467991"};
    }


    @Test
    public void validatePhoneNumber() {
        //given
        String phone = "+79967467999";
        //when
        boolean isValid = RussianPhoneValidator.validatePhoneNumber(phone);
        //then
        Assertions.assertTrue(isValid);
    }

    @ParameterizedTest
    @CsvSource("+79967467999, true")
    public void validatePhones(String phoneNumber, boolean expected) {
        boolean isValid = RussianPhoneValidator.validatePhoneNumber(phoneNumber);
        Assertions.assertEquals(expected, isValid);
    }
}