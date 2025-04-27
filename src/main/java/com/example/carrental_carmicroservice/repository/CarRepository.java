package com.example.carrental_carmicroservice.repository;

import com.example.carrental_carmicroservice.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends MongoRepository<Car, String> {
    List<Car> findByAvailable(boolean available);
}
