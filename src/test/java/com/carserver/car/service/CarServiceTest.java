package com.carserver.car.service;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.entity.RentalStatus;
import com.carserver.car.domain.repository.CarRepository;
import com.carserver.car.domain.repository.CategoryRepository;
import com.carserver.car.dto.*;
import com.carserver.car.dto.request.SaveCarRequest;
import com.carserver.car.dto.request.UpdateCarRequest;
import com.carserver.car.dto.response.CarAvailabilityResponse;
import com.carserver.car.dto.response.SaveCarResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CarServiceTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    void 신규_차량_저장_성공() {
        // given
        List<Category> savedCategories = categoryRepository.saveAll(List.of(new Category("소형"), new Category("SUV")));
        List<Long> categoryIds = savedCategories.stream()
                .map(Category::getId)
                .toList();
        SaveCarRequest request = new SaveCarRequest("현대", "코나", categoryIds, 2024);

        // when
        SaveCarResponse response = carService.save(request);

        // then
        Car findCar = carRepository.findByIdWithCategories(response.getId()).orElseThrow();
        assertThat(findCar.getBrand()).isEqualTo("현대");
        assertThat(findCar.getModel()).isEqualTo("코나");
        assertThat(findCar.getCategories()).hasSize(2)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("소형", "SUV");
        assertThat(findCar.getProductionYear()).isEqualTo(2024);
    }

    @Test
    @Transactional
    void 기존_차량_수정_성공() {
        // given
        List<Category> savedCategories = categoryRepository.saveAll(List.of(new Category("소형"), new Category("SUV")));
        Car car = new Car("현대", "코나", RentalStatus.AVAILABLE, savedCategories, 2022);
        Long savedId = carRepository.save(car).getId();

        savedCategories = categoryRepository.saveAll(List.of(new Category("중형"), new Category("세단")));
        List<Long> categoryIds = savedCategories.stream()
                .map(Category::getId)
                .toList();
        UpdateCarRequest request = new UpdateCarRequest("기아", "k5", RentalStatus.RENTED, categoryIds, 2024);

        // when
        SaveCarResponse response = carService.update(savedId, request);

        // then
        Car findCar = carRepository.findByIdWithCategories(response.getId()).orElseThrow();
        assertThat(findCar.getBrand()).isEqualTo("기아");
        assertThat(findCar.getModel()).isEqualTo("k5");
        assertThat(findCar.getRentalStatus()).isEqualTo(RentalStatus.RENTED);
        assertThat(findCar.getProductionYear()).isEqualTo(2024);
        assertThat(findCar.getCategories()).hasSize(2)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("중형", "세단");
        assertThat(findCar.getUpdatedDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }

    @Test
    @Transactional
    void 대여_가능_여부_조회_성공() {
        // given
        List<Category> savedCategories = categoryRepository.saveAll(List.of(new Category("소형"), new Category("SUV")));
        List<Car> cars = carRepository.saveAll(List.of(
                new Car("현대", "코나", RentalStatus.AVAILABLE, savedCategories, 2022),
                new Car("기아", "니로", RentalStatus.RENTED, savedCategories, 2023)
        ));
        List<Long> ids = cars.stream()
                .map(Car::getId)
                .collect(Collectors.toList());
        ids.addAll(List.of(1000L, 2000L, 3000L));

        // when
        CarAvailabilityResponse response = carService.findCarAvailability(ids);
        List<CarAvailabilityDTO> carAvailabilityDTOS = response.carAvailabilityDTOS();

        // then
        assertThat(carAvailabilityDTOS).hasSize(2)
                .extracting(CarAvailabilityDTO::getRentalStatus)
                .containsExactlyInAnyOrder(RentalStatus.AVAILABLE, RentalStatus.RENTED);
    }

    @Test
    @Transactional
    void 조건문에_따른_검색시_성공() {
        // given
        List<Category> smallCategories = categoryRepository.saveAll(List.of(new Category("소형"), new Category("SUV")));
        Car cona = new Car("hyundai", "cona", RentalStatus.AVAILABLE, new ArrayList<>(), 1996);
        Car niro = new Car("kia", "niro", RentalStatus.AVAILABLE, new ArrayList<>(), 1996);
        cona.addCategories(smallCategories);
        niro.addCategories(smallCategories);

        List<Category> mediumCategories = categoryRepository.saveAll(List.of(new Category("중형"), new Category("세단")));
        Car sonata = new Car("hyundai", "sonata", RentalStatus.AVAILABLE, new ArrayList<>(), 1996);
        Car k5 = new Car("kia", "k5", RentalStatus.AVAILABLE, new ArrayList<>(), 1996);
        sonata.addCategories(mediumCategories);
        k5.addCategories(mediumCategories);

        carRepository.saveAll(List.of(cona, niro, sonata, k5));

        // when
        CarSearchCondition hyundaiCondition = new CarSearchCondition("hyundai", null, null);
        CarSearchCondition conaCondition = new CarSearchCondition(null, "cona", null);
        CarSearchCondition yearCondition = new CarSearchCondition(null, null, 1996);

        List<CarWithCategoriesDTO> hyundaiResult = carService.search(hyundaiCondition).carWithCategoriesDTO();
        List<CarWithCategoriesDTO> conaResult = carService.search(conaCondition).carWithCategoriesDTO();
        List<CarWithCategoriesDTO> yearResult = carService.search(yearCondition).carWithCategoriesDTO();

        // then
        assertThat(hyundaiResult).hasSize(2)
                .extracting(CarWithCategoriesDTO::getBrand)
                .containsOnly("hyundai");

        assertThat(conaResult).hasSize(1)
                .extracting(CarWithCategoriesDTO::getModel)
                .containsOnly("cona");

        assertThat(yearResult).hasSize(4)
                .extracting(CarWithCategoriesDTO::getProductionYear)
                .containsOnly(1996);
    }
}