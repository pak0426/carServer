package com.carserver.car.service;

import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CategoryValidatorTest {

    @Autowired
    CategoryValidator categoryValidator;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void 존재하지_않는_카테고리_ID라면_실패() {
        // given
        categoryRepository.saveAll(List.of(new Category("소형"), new Category("중형")));

        // when, then
        assertThatThrownBy(() -> categoryValidator.validate(List.of(1000L, 2000L, 3000L)))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 존재하는_카테고리_ID라면_성공() {
        // given
        List<Category> savedCategories = categoryRepository.saveAll(List.of(new Category("소형"), new Category("중형")));
        List<Long> categoryIds = savedCategories.stream()
                .map(Category::getId)
                .toList();

        // when
        List<Category> categories = categoryValidator.validate(categoryIds);

        // then
        assertThat(categories).hasSize(2)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("소형", "중형");

    }
}