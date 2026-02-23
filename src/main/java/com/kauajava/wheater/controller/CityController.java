package com.kauajava.wheater.controller;

import com.google.gson.Gson;
import com.kauajava.wheater.model.CityData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CityController {
    private final HttpClient client;
    private final String API_KEY= "a04b957f3d8c849a2971ec0a71ccf144";

    public CityController() {
        this.client = HttpClient.newHttpClient();
    }

    public CityData getCity(String cityName) throws Exception {
        String url = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&appid=" + API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(url);

        String json = response.body();

        Gson gson = new Gson();
        CityData[] cityData = gson.fromJson(json, CityData[].class);

        if (cityData.length == 0) {
            throw new Exception("No city found");
        }
        return cityData[0];

    }

}
