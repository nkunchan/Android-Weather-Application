package com.example.weatherforecast;

/**
 * Created by Nikita on 10/19/2016.
 */
public class Forecast {
    String   cityname,country, date,Time,icon, temp, Condition, Pressure, Humidity, Wind;

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getWind() {
        return Wind;
    }

    public void setWind(String wind) {
        Wind = wind;
    }


    @Override
    public String toString() {
        return "Forecast{" +
                "cityname='" + cityname + '\'' +
                ", country='" + country + '\'' +
                ", date='" + date + '\'' +
                ", Time='" + Time + '\'' +
                ", icon='" + icon + '\'' +
                ", temp='" + temp + '\'' +
                ", Condition='" + Condition + '\'' +
                ", Pressure='" + Pressure + '\'' +
                ", Humidity='" + Humidity + '\'' +
                ", Wind='" + Wind + '\'' +
                '}';
    }
}
