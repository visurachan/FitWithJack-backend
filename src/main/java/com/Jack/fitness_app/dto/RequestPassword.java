package com.Jack.fitness_app.dto;

public class RequestPassword {
    private String password;

    public RequestPassword() {
    }

    public RequestPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
