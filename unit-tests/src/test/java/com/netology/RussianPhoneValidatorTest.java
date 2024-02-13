package com.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

//https://github.com/netology-code/jd-homeworks/tree/master/junit
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
    @ValueSource(strings = {"+79967467999", "89967467999"})
    public void validatePhones1(String phoneNumber) {
        boolean isValid = RussianPhoneValidator.validatePhoneNumber(phoneNumber);
        Assertions.assertTrue(isValid);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "89967467999, true",
            "79967467999, true",
            "+79967467999, true",
            "+19967467999, false",
            "+29967467999, false",
            "+0, false"
    })
    public void validatePhones(String phoneNumber, boolean expected) {
        boolean isValid = RussianPhoneValidator.validatePhoneNumber(phoneNumber);
        Assertions.assertEquals(expected, isValid);
    }
}

/*
Scenario: Login check
Given I am on the login page
When I enter "username" username
And I enter "Password" password
And I click on the "Login" button
Then I am able to login successfully.
 */