package org.example;

import org.example.enums.Preference;
import org.example.enums.Weather;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdviceServiceTestWithMockitoEnhanced {

    @Mock
    WeatherService weatherService;
    @Mock
    PreferencesService preferencesService;

    @InjectMocks
    AdviceService adviceService;

    @Test
    void testWithMockitoEnhanced() {
        when(weatherService.currentWeather()).thenReturn(Weather.STORMY);
        when(preferencesService.get(any())).thenReturn(
                Set.of(Preference.FOOTBALL, Preference.WATCHING_FILMS, Preference.READING)
        );

        Set<Preference> expected = Set.of(Preference.READING, Preference.WATCHING_FILMS);
        Set<Preference> actual = adviceService.getAdvice("userId");

        assertEquals(expected, actual);
    }

    @Test
    void testWithMockitoVerify() {
        when(weatherService.currentWeather()).thenReturn(Weather.STORMY);
        when(preferencesService.get(any())).thenReturn(
                Set.of(Preference.FOOTBALL, Preference.WATCHING_FILMS, Preference.READING)
        );

        Set<Preference> expected = Set.of(Preference.READING, Preference.WATCHING_FILMS);
        Set<Preference> actual = adviceService.getAdvice("userId");

        assertEquals(expected, actual);

        verify(weatherService, Mockito.times(1)).currentWeather();
        verify(preferencesService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    void testWithArgumentCapture() {
        when(weatherService.currentWeather()).thenReturn(Weather.STORMY);
        when(preferencesService.get(any())).thenReturn(Set.of(Preference.FOOTBALL));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        adviceService.getAdvice("userId");

        verify(preferencesService).get(argumentCaptor.capture());
        assertEquals("userId1", argumentCaptor.getValue());
    }
}
