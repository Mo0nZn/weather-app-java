package com.kauajava.wheater.view;

import com.kauajava.wheater.controller.WeatherController;
import com.kauajava.wheater.model.WeatherResponse;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WeatherView extends Application {

    WeatherController weatherController;


    @Override
    public void start(Stage stage) throws Exception {
        this.weatherController = new WeatherController();

        var searchBar =  new TextField();
        searchBar.setPrefSize(300, 40);

        var serchButton = new Button("Search");
        serchButton.setPrefSize(70, 40);


        var temperature = new Label("Temp°C");

        temperature.setStyle("""
        -fx-font-size: 48px;
        -fx-font-weight: bold;
    """);

        var weatherCondition = new Label("Weather");
        weatherCondition.setStyle("""
        -fx-font-size: 28px;
        -fx-font-weight: bold;
    """);

        var weatherIcon = new Image((Objects.requireNonNull(getClass().getResource("/images/sun-image2-nobg.png")).toExternalForm()));
        var weatherView = new ImageView(weatherIcon);

        var humidityIcon = new Image((Objects.requireNonNull(getClass().getResource("/images/humidity2-nobg.png")).toExternalForm()));
        var humidityCondition = new ImageView(humidityIcon);
        humidityCondition.setFitWidth(60);
        humidityCondition.setFitHeight(60);

        var windIcon = new Image((Objects.requireNonNull(getClass().getResource("/images/wind2-nobg.png")).toExternalForm()));
        var windCondition = new ImageView(windIcon);
        windCondition.setFitWidth(60);
        windCondition.setFitHeight(60);

        Label windSpeed = new Label("Wind Speed");
        windSpeed.setStyle("""
        -fx-font-size: 15px;
        -fx-font-weight: bold;
    """);

        Label humidityValue = new Label("Humidity");
        humidityValue.setStyle("""
        -fx-font-size: 15px;
        -fx-font-weight: bold;
    """);

        Region regionSpacer = new Region();
        HBox.setHgrow(regionSpacer, Priority.ALWAYS);

        var clearIcon = new Image((Objects.requireNonNull(getClass().getResource("/weatherImages/clear.png")).toExternalForm()));
        var snowIcon = new Image((Objects.requireNonNull(getClass().getResource("/weatherImages/snow.png")).toExternalForm()));
        var rainIcon = new Image((Objects.requireNonNull(getClass().getResource("/weatherImages/rain.png")).toExternalForm()));
        var cloudsIcon = new Image((Objects.requireNonNull(getClass().getResource("/weatherImages/clouds.png")).toExternalForm()));
        var thunderStormIcon = new Image((Objects.requireNonNull(getClass().getResource("/weatherImages/thunderStorm.png")).toExternalForm()));;


        BorderPane root = new BorderPane();
        HBox top = new HBox(searchBar, serchButton);
        top.setAlignment(Pos.TOP_CENTER);
        root.setTop(top);
        VBox center = new VBox(weatherView, temperature, weatherCondition);
        center.setAlignment(Pos.CENTER);
        root.setCenter(center);
        HBox bottom= new HBox(humidityCondition, humidityValue, regionSpacer, windSpeed, windCondition);
        bottom.setPadding(new Insets(10));
        bottom.setSpacing(10);
        humidityValue.setPadding(new Insets(20, 0, 0, 0));
        windSpeed.setPadding(new Insets(20, 0, 0, 0));
        root.setBottom(bottom);

        serchButton.setOnMouseClicked(event -> {
            try {
                WeatherResponse weatherResponse = weatherController.getWeather(searchBar.getText());
                temperature.setText(weatherResponse.main.temp + "°C");
                weatherCondition.setText(weatherResponse.weather[0].description);
                windSpeed.setText(String.valueOf(weatherResponse.wind.speed + "km/h"));
                humidityValue.setText(weatherResponse.main.humidity + "%");

                if(weatherResponse.weather[0].main.equals("Clear")){
                    weatherView.setImage(clearIcon);
                } else if (weatherResponse.weather[0].main.equals("Rain")) {
                    weatherView.setImage(rainIcon);
                }else if (weatherResponse.weather[0].main.equals("ThunderStorm")) {
                    weatherView.setImage(thunderStormIcon);
                }else if (weatherResponse.weather[0].main.equals("Clouds")) {
                    weatherView.setImage(cloudsIcon);
                }else if (weatherResponse.weather[0].main.equals("Snow")) {
                    weatherView.setImage(snowIcon);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        var scene = new Scene(root, 400, 500);
        stage.setTitle("Weather");
        stage.setScene(scene);
        stage.show();
    }
}
