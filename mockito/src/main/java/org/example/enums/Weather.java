package org.example.enums;

public enum Weather {
    RAINY("Дождливо"),
    STORMY("Сильный ветер"),
    SUNNY("Солнечно"),
    CLOUDLY("Облачно");

    private final String weather;

    Weather(String weather) {
        this.weather = weather;
    }
}
