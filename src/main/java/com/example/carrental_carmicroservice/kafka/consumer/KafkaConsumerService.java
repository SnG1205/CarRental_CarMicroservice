package com.example.carrental_carmicroservice.kafka.consumer;

import com.example.carrental_carmicroservice.dto.BookingCloseRequestedMessage;
import com.example.carrental_carmicroservice.dto.BookingCreatedMessage;
import com.example.carrental_carmicroservice.dto.BookingRequestedMessage;
import com.example.carrental_carmicroservice.model.Car;
import com.example.carrental_carmicroservice.service.CarService;
import com.example.carrental_carmicroservice.utils.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class KafkaConsumerService {
    private JsonConverter jsonConverter = new JsonConverter();
    @Autowired
    private CarService carService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "booking-requested-topic", groupId = "my-group")
    public void consumeBookingRequestedMessage(String message) throws JsonProcessingException {
        System.out.println("Received booking requested message: " + message);
        BookingRequestedMessage bookingRequestedMessage = jsonConverter.fromJson(message, BookingRequestedMessage.class);
        String carId = bookingRequestedMessage.getCarId();
        Optional<Car> car = carService.findById(carId);
        if (car.isPresent()) {
            Car carToSave = car.get();
            carToSave.setAvailable(false);
            carService.save(carToSave);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.from(dtf.parse(bookingRequestedMessage.getStartDate()));
            LocalDate endDate = LocalDate.from(dtf.parse(bookingRequestedMessage.getEndDate()));
            long days = ChronoUnit.DAYS.between(startDate, endDate); //Todo mb add + 1
            double totalCost = carToSave.getPricePerDayUsd() * days;
            BookingCreatedMessage bookingCreatedMessage = new BookingCreatedMessage(
                    bookingRequestedMessage.getUserId(),
                    bookingRequestedMessage.getCarId(),
                    bookingRequestedMessage.getStartDate(),
                    bookingRequestedMessage.getEndDate(),
                    totalCost
            );
            kafkaTemplate.send("booking-created-topic", jsonConverter.toJson(bookingCreatedMessage));
        }
    }

    @KafkaListener(topics = "booking-close-topic", groupId = "my-group")
    public void consumeBookingCloseMessage(String message) throws JsonProcessingException {
        System.out.println("Received booking close message: " + message);
        BookingCloseRequestedMessage bookingCloseRequestedMessage = jsonConverter.fromJson(message, BookingCloseRequestedMessage.class);
        String carId = bookingCloseRequestedMessage.getCarId();
        Optional<Car> car = carService.findById(carId);
        if (car.isPresent()) {
            Car carToSave = car.get();
            carToSave.setAvailable(true);
            carService.save(carToSave);
            kafkaTemplate.send("booking-ended-topic", jsonConverter.toJson(bookingCloseRequestedMessage));
        }

    }
}
