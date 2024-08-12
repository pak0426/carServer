package com.carserver.car.service;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.repository.CarRepository;
import com.carserver.car.domain.repository.CategoryRepository;
import com.carserver.car.dto.SaveCarRequest;
import com.carserver.car.dto.SaveCarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CategoryValidator categoryValidator;

    @Transactional
    public SaveCarResponse save(SaveCarRequest request) {
        List<Category> categories = categoryValidator.validate(request.getCategoryIds());
        Car car = request.toEntity(categories);
        return SaveCarResponse.from(carRepository.save(car));
    }
}
