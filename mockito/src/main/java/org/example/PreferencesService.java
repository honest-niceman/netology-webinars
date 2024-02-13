package org.example;

import org.example.enums.Preference;

import java.util.Set;

public interface PreferencesService {
    Set<Preference> get(String userId);
}
