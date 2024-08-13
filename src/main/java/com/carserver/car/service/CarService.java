package com.carserver.car.service;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.repository.CarRepository;
import com.carserver.car.dto.*;
import com.carserver.car.dto.request.SaveCarRequest;
import com.carserver.car.dto.request.UpdateCarRequest;
import com.carserver.car.dto.response.CarAvailabilityResponse;
import com.carserver.car.dto.response.CarWithCategoriesResponse;
import com.carserver.car.dto.response.SaveCarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarValidator carValidator;
    private final CategoryValidator categoryValidator;

    @Transactional
    public SaveCarResponse save(SaveCarRequest request) {
        List<Category> categories = categoryValidator.validate(request.getCategoryIds());
        Car car = request.toEntity(categories);
        return SaveCarResponse.from(carRepository.save(car));
    }

    @Transactional
    public SaveCarResponse update(Long id, UpdateCarRequest request) {
        Car car = carValidator.validate(id);
        List<Category> categories = categoryValidator.validate(request.getCategoryIds());
        car.update(request.toEntity(categories));
        return SaveCarResponse.from(carRepository.save(car));
    }

    public CarAvailabilityResponse findCarAvailability(List<Long> ids) {
        List<Car> cars = carRepository.findAllByIds(ids);
        List<CarAvailabilityDTO> carAvailabilityDTOS = cars.stream()
                .map(CarAvailabilityDTO::from)
                .toList();
        return new CarAvailabilityResponse(carAvailabilityDTOS);
    }

    public CarWithCategoriesResponse search(CarSearchCondition condition) {
        List<Car> cars = carRepository.findCarsByCondition(condition);
        List<CarWithCategoriesDTO> carWithCategoriesDTOS = cars.stream()
                .map(CarWithCategoriesDTO::from)
                .toList();
        return new CarWithCategoriesResponse(carWithCategoriesDTOS);
    }
}
