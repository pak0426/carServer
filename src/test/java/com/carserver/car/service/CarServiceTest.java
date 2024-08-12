package com.carserver.car.service;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.repository.CarRepository;
import com.carserver.car.domain.repository.CategoryRepository;
import com.carserver.car.dto.SaveCarRequest;
import com.carserver.car.dto.SaveCarResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
                .collect(Collectors.toList());
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
}