package com.ddb.users.Entities;

import android.location.Location;

import com.ddb.users.Entities.Enums.Gender;


public class User {

    String phone_number;
    String first_name;
    String last_name;
    String mail_address;
    //Location address;
    Address address;
    Gender gender;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public User(String phone_number, String first_name, String last_name, String mail_address, Address address, Gender gender) {
        this.phone_number = phone_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mail_address = mail_address;
        this.address = address;
        this.gender = gender;
    }

    public User() {
        this.phone_number = "";
        this.first_name = "";
        this.last_name = "";
        this.mail_address = "";
        this.address = new Address();
        this.gender = Gender.MALE;


    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMail_address() {
        return mail_address;
    }

    public void setMail_address(String mail_address) {
        this.mail_address = mail_address;
    }


    public static Gender stringToGender(String s) {

        switch (s) {
            case "0":
                return Gender.MALE;
            case "1":
                return Gender.FEMALE;
            default:
                return Gender.MALE;

        }
    }

//    public Location getAddress() {
//        return address;
//    }
//
//   // public void setAddress(Location address) {
//        this.address = address;
//    }

    @Override
    public String toString() {
        return "User{" +
                "phone number=" + phone_number +
                ", first name=" + first_name +
                ", last name=" + last_name +
                ", mail=" + mail_address +
                //    ", address=" + address +
                '}';
    }


}
