package com.carserver.car.domain.repository;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.assertj.core.api.Assertions;
import org.hibernate.TransientObjectException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 필수값이_존재한다면_저장_성공() {
        // given
        List<Category> savedCategories = categoryRepository.saveAll(List.of(new Category("소형"), new Category("SUV")));
        Car car = new Car("현대", "코나", savedCategories, 2024);

        // when
        Car savedCar = carRepository.save(car);

        // then
        assertThat(savedCar.getBrand()).isEqualTo("현대");
        assertThat(savedCar.getModel()).isEqualTo("코나");
        assertThat(savedCar.getProductionYear()).isEqualTo(2024);
        assertThat(savedCar.getCategories()).hasSize(2)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("소형", "SUV");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCars")
    void 필수값이_누락됐다면_저장_실패(Car invalidCar) {
        assertThatThrownBy(() -> carRepository.save(invalidCar))
                .isInstanceOf(ConstraintViolationException.class);
    }

    private static Stream<Arguments> provideInvalidCars() {
        List<Category> savedCategories = List.of(new Category("소형"), new Category("SUV"));
        return Stream.of(
                Arguments.of("카테고리 없음", new Car("현대", "코나", new ArrayList<>(), 2024)),
                Arguments.of("브랜드 없음", new Car("", "코나", savedCategories, 2024)),
                Arguments.of("모델 없음", new Car("현대", "", savedCategories, 2024)),
                Arguments.of("유효하지 않은 연도", new Car("현대", "코나", savedCategories, 1000))
        );
    }

    @Test
    void 존재하는_카테고리라면_카테고리_추가_성공() {
        // given
        List<Category> savedCategories = categoryRepository.saveAll(List.of(new Category("소형")));
        Car car = new Car("현대", "코나", savedCategories, 2024);
        Car savedCar = carRepository.save(car);
        List<Category> categories = categoryRepository.saveAll(List.of(new Category("SUV")));

        // when
        savedCar.addCategories(categories);
        Car saved = carRepository.save(savedCar);

        // then
        assertThat(saved.getCategories()).hasSize(2)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("소형", "SUV");
    }

    @Test
    void 존재하지_않는_카테고리라면_카테고리_추가_실패() {
        // given
        List<Category> savedCategories = categoryRepository.saveAll(List.of(new Category("소형")));
        Car car = new Car("현대", "코나", savedCategories, 2024);
        Car savedCar = carRepository.save(car);
        List<Category> categories = List.of(new Category("SUV"));

        // when
        savedCar.addCategories(categories);

        // then
        assertThatThrownBy(() -> carRepository.save(savedCar))
                .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }
}