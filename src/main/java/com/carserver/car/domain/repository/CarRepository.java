package com.carserver.car.domain.repository;

import com.carserver.car.domain.entity.Car;

import java.util.Optional;

public interface CarRepository {
    Car save(Car car);
    Optional<Car> findByIdWithCategories(Long id);
}
