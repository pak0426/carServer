package com.carserver.car.dto;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.RentalStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CarWithCategoriesDTO {
    private Long id;
    private String brand;
    private String model;
    private RentalStatus rentalStatus;
    private int productionYear;
    private List<CategoryDTO> categories;

    public static CarWithCategoriesDTO from(Car car) {
        return CarWithCategoriesDTO.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .rentalStatus(car.getRentalStatus())
                .productionYear(car.getProductionYear())
                .categories(car.getCategories().stream()
                        .map(CategoryDTO::from)
                        .toList())
                .build();
    }
}
