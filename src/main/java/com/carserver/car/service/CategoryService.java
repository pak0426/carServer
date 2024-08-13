package com.carserver.car.service;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.repository.CategoryRepository;
import com.carserver.car.dto.CarDTO;
import com.carserver.car.dto.CategoryWithCarsDTO;
import com.carserver.car.dto.response.CategoryWithCarsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryWithCarsResponse getCategoriesWithCars(List<Long> categoryIds) {
        List<Category> categories = categoryRepository.findAllByIds(categoryIds);
        List<CategoryWithCarsDTO> categoryWithCarsDTOS = categories.stream()
                .map(CategoryWithCarsDTO::from)
                .toList();
        return new CategoryWithCarsResponse(categoryWithCarsDTOS);
    }
}
