package com.example.carrental_carmicroservice.dto;

public class ValidCar {

    private String id;

    public ValidCar() {
    }

    public ValidCar(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
