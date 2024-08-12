package com.carserver.car.dto;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class SaveCarResponse {
    private Long id;
    private String brand;
    private String model;
    private int productionYear;
    private List<Category> categories;
    private LocalDateTime createdDate;

    public static SaveCarResponse from(Car car) {
        return SaveCarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .productionYear(car.getProductionYear())
                .categories(car.getCategories())
                .createdDate(car.getCreatedDate())
                .build();
    }
}
