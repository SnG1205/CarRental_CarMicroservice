package com.example.carrental_carmicroservice.controller;

import com.example.carrental_carmicroservice.model.Car;
import com.example.carrental_carmicroservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/available")
    public List<Car> getAvailableCars() {
        return carService.getAvailableCars();
    }

    @PostMapping
    public Car addCar(@RequestBody Car car) {
        car.setAvailable(true);
        return carService.addCar(car); //Todo split into 2 lines and publish Kafka message after save to notify BookingService about carId
    }
}
