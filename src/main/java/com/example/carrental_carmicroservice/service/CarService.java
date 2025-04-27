package com.example.carrental_carmicroservice.service;

import com.example.carrental_carmicroservice.model.Car;
import com.example.carrental_carmicroservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAvailableCars() {
        return carRepository.findByAvailable(true);
    }

    public Optional<Car> findById(String id) {
        return carRepository.findById(id);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public void delete(Car car) {
        carRepository.delete(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}
