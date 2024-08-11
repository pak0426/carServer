package com.carserver.car.domain.repository;

import com.carserver.car.domain.entity.Car;

public interface CarRepository {
    Car save(Car car);
}
