package com.example.carrental_carmicroservice.dto;

public class BookingCloseRequestedMessage {
    private String bookingId;
    private String carId;

    public BookingCloseRequestedMessage() {
    }

    public BookingCloseRequestedMessage(String bookingId, String carId) {
        this.bookingId = bookingId;
        this.carId = carId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
