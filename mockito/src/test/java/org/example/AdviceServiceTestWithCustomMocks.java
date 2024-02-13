package org.example;

import org.example.enums.Preference;
import org.example.enums.Weather;
import org.example.mocks.PreferenceServiceMock;
import org.example.mocks.WeatherServiceMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class AdviceServiceTestWithCustomMocks {
    @Test
    void testWithCustomMocks() {
        WeatherServiceMock weatherServiceMock = new WeatherServiceMock();
        weatherServiceMock.setValue(Weather.STORMY);

        PreferenceServiceMock preferenceServiceMock = new PreferenceServiceMock();
        preferenceServiceMock.setValue(Set.of(Preference.FOOTBALL, Preference.WATCHING_FILMS, Preference.READING));

        AdviceService adviceService = new AdviceService(preferenceServiceMock, weatherServiceMock);

        Set<Preference> expected = Set.of(Preference.READING, Preference.WATCHING_FILMS);

        Set<Preference> actual = adviceService.getAdvice("userId");

        Assertions.assertEquals(expected, actual);
    }
}
