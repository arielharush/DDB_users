package com.ddb.users.Entities;

public class Address {
    String country;
    String city;
    String street;
    String number;
    String zipCode;
    double longitude;
    double latitude;

    public Address(String country, String city, String street, String number, String zipCode, double longitude, double latitude) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Address() {
        this.country = "";
        this.city = "";
        this.street = "";
        this.number = "";
        this.zipCode = "";
        this.longitude = 0;
        this.latitude = 0;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
