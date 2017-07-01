package com.example.weatherforecast;

/**
 * Created by Nikita on 10/19/2016.
 */
public class Favorites {
    String city,country,temp;
    String favorite;
    String updatedDate;

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Favorites(String city, String country, String temp, String favorite) {
        this.city=city;
        this.country=country;
        this.temp = temp;
        this.favorite = favorite;

    }
    public Favorites(){}

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Favorites{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", temp='" + temp + '\'' +
                ", favorite='" + favorite + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
    }
}
