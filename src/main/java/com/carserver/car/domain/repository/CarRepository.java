package com.carserver.car.domain.repository;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.dto.CarSearchCondition;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Car save(Car car);
    List<Car> saveAll(List<Car> cars);
    Optional<Car> findById(Long id);
    Optional<Car> findByIdWithCategories(Long id);
    List<Car> findAllByIds(List<Long> ids);
    List<Car> findCarsByCondition(CarSearchCondition condition);
}
