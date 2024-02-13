package org.example;

import org.example.enums.Preference;
import org.example.enums.Weather;
import org.example.mocks.PreferenceServiceMock;
import org.example.mocks.WeatherServiceMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Set;

class AdviceServiceTestWithMockito {
    @Test
    void testWithMockito() {
        WeatherService weatherService = Mockito.mock(WeatherService.class);
        PreferencesService preferencesService = Mockito.mock(PreferencesService.class);

        Mockito.when(weatherService.currentWeather()).thenReturn(Weather.STORMY);
        Mockito.when(preferencesService.get("userId")).thenReturn(
                Set.of(Preference.FOOTBALL, Preference.WATCHING_FILMS, Preference.READING)
        );

        AdviceService adviceService = new AdviceService(preferencesService, weatherService);

        Set<Preference> expected = Set.of(Preference.READING, Preference.WATCHING_FILMS);

        Set<Preference> actual = adviceService.getAdvice("userId");

        Assertions.assertEquals(expected, actual);
    }
}
