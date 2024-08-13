package com.carserver.car.service;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.entity.RentalStatus;
import com.carserver.car.domain.repository.CarRepository;
import com.carserver.car.domain.repository.CategoryRepository;
import com.carserver.car.dto.CarDTO;
import com.carserver.car.dto.CategoryWithCarsDTO;
import com.carserver.car.dto.response.CategoryWithCarsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    @Transactional
    void 카테고리별_차량_조회에_성공() {
        // given
        List<Category> smallCategories = categoryRepository.saveAll(List.of(new Category("소형"), new Category("SUV")));

        Car cona = new Car("현대", "코나", RentalStatus.AVAILABLE, new ArrayList<>(), 2024);
        Car niro = new Car("기아", "니로", RentalStatus.AVAILABLE, new ArrayList<>(), 2024);
        cona.addCategories(smallCategories);
        niro.addCategories(smallCategories);

        List<Category> mediumCategories = categoryRepository.saveAll(List.of(new Category("중형"), new Category("세단")));

        Car sonata = new Car("현대", "쏘나타", RentalStatus.AVAILABLE, new ArrayList<>(), 2024);
        Car k5 = new Car("기아", "k5", RentalStatus.AVAILABLE, new ArrayList<>(), 2024);
        sonata.addCategories(mediumCategories);
        k5.addCategories(mediumCategories);

        carRepository.saveAll(List.of(cona, niro, sonata, k5));

        List<Long> categoryIds = new ArrayList<>();
        categoryIds.addAll(smallCategories.stream()
                .map(Category::getId)
                .toList());
        categoryIds.addAll(mediumCategories.stream()
                .map(Category::getId)
                .toList());
        categoryIds.addAll(List.of(1000L, 2000L, 3000L));

        // when
        CategoryWithCarsResponse response = categoryService.getCategoriesWithCars(categoryIds);
        List<CategoryWithCarsDTO> categoryWithCarsDTOS = response.categoryWithCarsDTOS();

        // then
        assertThat(categoryWithCarsDTOS).hasSize(4)
                .extracting(CategoryWithCarsDTO::getName)
                .containsExactlyInAnyOrder("소형", "SUV", "중형", "세단");

        categoryWithCarsDTOS.forEach(category -> {
            switch(category.getName()) {
                case "소형", "SUV" -> {
                    assertThat(category.getCars()).hasSize(2)
                            .extracting(CarDTO::getModel)
                            .containsExactlyInAnyOrder("코나", "니로");
                    assertThat(category.getCars()).hasSize(2)
                            .extracting(CarDTO::getBrand)
                            .containsExactlyInAnyOrder("현대", "기아");
                }

                case "중형", "세단" -> {
                    assertThat(category.getCars()).hasSize(2)
                            .extracting(CarDTO::getModel)
                            .containsExactlyInAnyOrder("쏘나타", "k5");
                    assertThat(category.getCars()).hasSize(2)
                            .extracting(CarDTO::getBrand)
                            .containsExactlyInAnyOrder("현대", "기아");
                }
            }
        });
    }
}