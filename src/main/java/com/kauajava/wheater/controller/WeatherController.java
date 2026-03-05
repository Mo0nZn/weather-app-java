package com.kauajava.wheater.controller;

import com.google.gson.Gson;
import com.kauajava.wheater.model.CityData;
import com.kauajava.wheater.model.WeatherResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherController {
    private final HttpClient client;
    private final String API_KEY= System.getenv("API_WEATHER_KEY");
    private final CityController cityController =  new CityController();

    public WeatherController() {
        this.client = HttpClient.newHttpClient();
    }

    public WeatherResponse getWeather(String name) throws Exception {
        if (name.contains(" ")) {
            name = name.split(" ")[0] + "-" + name.split(" ")[1];
        }

        CityData city = cityController.getCity(name);

        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + city.lat + "&lon=" + city.lon + "&units=metric&appid=" + API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(url);
        String json = response.body();

        Gson gson = new Gson();

        return gson.fromJson(json, WeatherResponse.class);
    }
}
