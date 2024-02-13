package org.example.mocks;

import org.example.PreferencesService;
import org.example.WeatherService;
import org.example.enums.Preference;
import org.example.enums.Weather;

import java.util.Set;

public class PreferenceServiceMock implements PreferencesService {
    private Set<Preference> value;

    public void setValue(Set<Preference> value) {
        this.value = value;
    }

    @Override
    public Set<Preference> get(String userId) {
        return value;
    }
}
