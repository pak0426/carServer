package com.carserver.car.service;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class CategoryValidator {
    private final CategoryRepository categoryRepository;

    public List<Category> validate(List<Long> categoryIds) {
        List<Category> categories = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
            categories.add(category);
        }
        return categories;
    }
}
