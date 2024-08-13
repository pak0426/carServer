package com.carserver.car.controller;

import com.carserver.car.dto.response.CategoryWithCarsResponse;
import com.carserver.car.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories/cars")
    public ResponseEntity<CategoryWithCarsResponse> getCategoriesWithCars(@RequestParam("categoryIds") List<Long> categoryIds) {
        return ResponseEntity.ok(categoryService.getCategoriesWithCars(categoryIds));
    }
}
