package org.example;

import org.example.enums.Preference;
import org.example.enums.Weather;

import java.util.Set;
import java.util.stream.Collectors;

public class AdviceService {
    private final PreferencesService preferencesService;
    private final WeatherService weatherService;

    public AdviceService(PreferencesService preferencesService, WeatherService weatherService) {
        this.preferencesService = preferencesService;
        this.weatherService = weatherService;
    }

    public Set<Preference> getAdvice(String userId) {
        Weather weather = weatherService.currentWeather();
        Set<Preference> preferences = preferencesService.get(userId + "1");

        if (Weather.RAINY == weather || Weather.STORMY == weather) {
            return preferences.stream()
                    .filter(p -> p != Preference.FOOTBALL)
                    .collect(Collectors.toSet());
        } else if (Weather.SUNNY == weather) {
            return preferences.stream()
                    .filter(p -> p != Preference.READING)
                    .collect(Collectors.toSet());
        }
        return preferences;
    }
}
