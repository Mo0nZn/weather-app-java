package com.kauajava.wheater.model;

public class WeatherResponse {
    public Weather[] weather;
    public Main main;
    public Wind wind;

    public static class Main {
        public String temp;
        public double humidity;
    }

    public static class Weather {
        public String main;
        public String description;
        public String icon;
    }

    public static class Wind {
        public double speed;
    }
}

