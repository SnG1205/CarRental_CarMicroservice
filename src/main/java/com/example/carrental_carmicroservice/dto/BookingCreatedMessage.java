package com.example.carrental_carmicroservice.dto;

public class BookingCreatedMessage {
    private String userId;
    private String carId;
    private String startDate;
    private String endDate;
    private double totalPriceUsd;

    public BookingCreatedMessage() {
    }

    public BookingCreatedMessage(String userId, String carId, String startDate, String endDate, double totalPriceUsd) {
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPriceUsd = totalPriceUsd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getTotalPriceUsd() {
        return totalPriceUsd;
    }

    public void setTotalPriceUsd(double totalPriceUsd) {
        this.totalPriceUsd = totalPriceUsd;
    }
}
