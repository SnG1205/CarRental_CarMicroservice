package com.example.carrental_carmicroservice.controller;

import com.example.carrental_carmicroservice.dto.ValidCar;
import com.example.carrental_carmicroservice.kafka.producer.KafkaProducerService;
import com.example.carrental_carmicroservice.model.Car;
import com.example.carrental_carmicroservice.service.CarService;
import com.example.carrental_carmicroservice.utils.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cars")
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private KafkaProducerService kafkaProducerService;
    private JsonConverter jsonConverter = new JsonConverter();

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/available")
    public List<Car> getAvailableCars() {
        return carService.getAvailableCars();
    }

    @PostMapping
    public Car addCar(@RequestBody Car car) throws JsonProcessingException {
        car.setAvailable(true);
        Car createdCar = carService.save(car);
        ValidCar validCar = new ValidCar(createdCar.getId());
        kafkaProducerService.sendCarCreatedMessage("car-created-topic", jsonConverter.toJson(validCar));
        return createdCar; //Todo split into 2 lines and publish Kafka message after save to notify BookingService about carId
    }

    @PostMapping("/kafka")
    public String sendKafka() throws JsonProcessingException {
        ValidCar validCar = new ValidCar("sss2");
        kafkaProducerService.sendCarCreatedMessage("car-created-topic", jsonConverter.toJson(validCar));
        return "success";
    }

    @DeleteMapping
    public String deleteCar(@RequestBody Car car) throws JsonProcessingException {
        carService.delete(car);
        return "Sss";
    }
}
