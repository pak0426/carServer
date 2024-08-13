package com.carserver.car.service;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class CarValidator {
    private final CarRepository carRepository;

    public Car validate(Long id) {
        return carRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
