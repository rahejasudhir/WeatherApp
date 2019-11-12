package com.an.trailers.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("coord")
    private Coord CoordObject;
    @SerializedName("weather")
    private Weather WeatherObject;
    @SerializedName("wind")
    private Wind WindObject;
    @SerializedName("rain")
    private Rain RainObject;

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "CoordObject=" + CoordObject +
                ", WeatherObject=" + WeatherObject +
                ", WindObject=" + WindObject +
                ", RainObject=" + RainObject +
                ", CloudsObject=" + CloudsObject +
                ", name='" + name + '\'' +
                '}';
    }

    @SerializedName("clouds")
    private Clouds CloudsObject;
    private String name;


    // Getter Methods

    public Coord getCoord() {
        return CoordObject;
    }

    public Weather getWeather() {
        return WeatherObject;
    }

    public Wind getWind() {
        return WindObject;
    }

    public Rain getRain() {
        return RainObject;
    }

    public Clouds getClouds() {
        return CloudsObject;
    }

    public String getName() {
        return name;
    }

    // Setter Methods

    public void setCoord(Coord coordObject) {
        this.CoordObject = coordObject;
    }

    public void setWeather(Weather weatherObject) {
        this.WeatherObject = weatherObject;
    }

    public void setWind(Wind windObject) {
        this.WindObject = windObject;
    }

    public void setRain(Rain rainObject) {
        this.RainObject = rainObject;
    }

    public void setClouds(Clouds cloudsObject) {
        this.CloudsObject = cloudsObject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Coord {
        private float lon;
        private float lat;


        // Getter Methods

        public float getLon() {
            return lon;
        }

        public float getLat() {
            return lat;
        }

        // Setter Methods

        public void setLon(float lon) {
            this.lon = lon;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "lon=" + lon +
                    ", lat=" + lat +
                    '}';
        }
    }

    public class Clouds {
        private float cloudiness;


        // Getter Methods

        public float getCloudiness() {
            return cloudiness;
        }

        // Setter Methods

        public void setCloudiness(float cloudiness) {
            this.cloudiness = cloudiness;
        }

        @Override
        public String toString() {
            return "Clouds{" +
                    "cloudiness=" + cloudiness +
                    '}';
        }
    }

    public static class Rain {
        @SerializedName("3h")
        private int hour;


        // Getter Methods

        public int get3h() {
            return hour;
        }

        // Setter Methods

        public void set3h(int hour) {
            this .hour = hour;
        }

        @Override
        public String toString() {
            return "Rain{" +
                    "hour=" + hour +
                    '}';
        }
    }

    public static class Wind {
        private float speed;
        private float deg;


        // Getter Methods

        public float getSpeed() {
            return speed;
        }

        public float getDeg() {
            return deg;
        }

        // Setter Methods

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public void setDeg(float deg) {
            this.deg = deg;
        }

        @Override
        public String toString() {
            return "Wind{" +
                    "speed=" + speed +
                    ", deg=" + deg +
                    '}';
        }
    }

    public static class Weather {
        private float temp;
        private float pressure;
        private float humidity;


        // Getter Methods

        public float getTemp() {
            return temp;
        }

        public float getPressure() {
            return pressure;
        }

        public float getHumidity() {
            return humidity;
        }

        // Setter Methods

        public void setTemp(float temp) {
            this.temp = temp;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }

        @Override
        public String toString() {
            return "Weather{" +
                    "temp=" + temp +
                    ", pressure=" + pressure +
                    ", humidity=" + humidity +
                    '}';
        }
    }
}




