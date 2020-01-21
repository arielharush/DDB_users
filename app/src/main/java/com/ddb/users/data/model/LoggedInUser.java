package com.ddb.users.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String phone;


    public LoggedInUser(String phone, String displayName) {
        this.phone = phone;

    }

    public String getPhone() {
        return phone;
    }


}
