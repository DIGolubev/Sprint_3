package com.example;

public class CourierCredentials {
    public String login;
    public String password;

    public CourierCredentials(String login, String password){
        this.login = login;
        this.password = password;
    }
    public CourierCredentials() {

    }

    public static CourierCredentials from (Courier courier){
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }
    @Override
    public String toString() {
        return "CourierLogin {" +
                "login:" + login + "," +
                "password:" + password + "}";
    }


}
