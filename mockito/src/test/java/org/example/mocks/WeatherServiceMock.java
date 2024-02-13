package org.example.mocks;

import org.example.WeatherService;
import org.example.enums.Weather;

public class WeatherServiceMock implements WeatherService {
    private Weather value;

    @Override
    public Weather currentWeather() {
        return value;
    }

    public void setValue(Weather value) {
        this.value = value;
    }
}
