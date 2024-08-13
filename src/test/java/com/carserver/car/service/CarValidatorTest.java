package com.carserver.car.service;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.entity.RentalStatus;
import com.carserver.car.domain.repository.CarRepository;
import com.carserver.car.domain.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarValidatorTest {

    @Autowired
    private CarValidator carValidator;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 존재하지_않는_차량_ID라면_실패() {
        // given
        List<Category> savedCategories = categoryRepository.saveAll(List.of(new Category("소형"), new Category("SUV")));
        Long savedId = carRepository.save(new Car("현대", "코나", RentalStatus.AVAILABLE, savedCategories, 2022)).getId();

        // when, then
        assertThatThrownBy(() -> carValidator.validate(savedId + 1))
                        .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 존재하는_차량_ID라면_성공() {
        // given
        List<Category> savedCategories = categoryRepository.saveAll(List.of(new Category("소형"), new Category("SUV")));
        Car savedCar = carRepository.save(new Car("현대", "코나", RentalStatus.AVAILABLE, savedCategories, 2022));

        // when
        Car findCar = carValidator.validate(savedCar.getId());

        // then
        assertThat(findCar.getId()).isEqualTo(savedCar.getId());
        assertThat(findCar.getModel()).isEqualTo(savedCar.getModel());
        assertThat(findCar.getBrand()).isEqualTo(savedCar.getBrand());
        assertThat(findCar.getRentalStatus()).isEqualTo(savedCar.getRentalStatus());
        assertThat(findCar.getProductionYear()).isEqualTo(savedCar.getProductionYear());
    }
}