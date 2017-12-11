package edu.sjsu.jen.cozplai;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jen0e on 12/10/17.
 */

public class WeatherApiResult {

    public Coord coord;
    public List<Weather> weatherList;
    public String base;
    public Main main;
    public Wind wind;
    public Rain rain;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int id;
    public String name;
    public int cod;

    public static WeatherApiResult fromString(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<WeatherApiResult>(){}.getType();

        return gson.fromJson(json, type);
    }

    public static class Coord {
        private double lat;
        private double lon;

        public Coord(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }

    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        public Weather(int id, String main, String description, String icon) {
            this.id = id;
            this.main = main;
            this.description = description;
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class Main {
        private double temp;
        private double pressure;
        private int humidity;
        private double temp_min;
        private double temp_max;

        public Main(double temp, double pressure, int humidity, double temp_min, double temp_max) {
            this.temp = temp;
            this.pressure = pressure;
            this.humidity = humidity;
            this.temp_min = temp_min;
            this.temp_max = temp_max;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }
    }

    public static class Wind {
        private double speed;
        private double deg;

        public Wind(double speed, double deg) {
            this.speed = speed;
            this.deg = deg;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public double getDeg() {
            return deg;
        }

        public void setDeg(double deg) {
            this.deg = deg;
        }
    }

    public static class Rain {

    }

    public static class Clouds  {
        private int all;

        public Clouds(int all) {
            this.all = all;
        }

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }
    }

    public static class Sys {
        private int type;
        private int id;
        private double message;
        private String country;
        private double sunrises;
        private double sunset;

        public Sys(int type, int id, double message, String country, double sunrises, double sunset) {
            this.type = type;
            this.id = id;
            this.message = message;
            this.country = country;
            this.sunrises = sunrises;
            this.sunset = sunset;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getMessage() {
            return message;
        }

        public void setMessage(double message) {
            this.message = message;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public double getSunrises() {
            return sunrises;
        }

        public void setSunrises(double sunrises) {
            this.sunrises = sunrises;
        }

        public double getSunset() {
            return sunset;
        }

        public void setSunset(double sunset) {
            this.sunset = sunset;
        }
    }
}
