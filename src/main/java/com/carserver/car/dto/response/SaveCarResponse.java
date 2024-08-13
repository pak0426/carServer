package com.carserver.car.dto.response;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.RentalStatus;
import com.carserver.car.dto.CategoryDTO;
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
    private RentalStatus rentalStatus;
    private int productionYear;
    private List<CategoryDTO> categories;
    private LocalDateTime createdDate;

    public static SaveCarResponse from(Car car) {
        return SaveCarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .rentalStatus(car.getRentalStatus())
                .productionYear(car.getProductionYear())
                .categories(car.getCategories().stream()
                        .map(CategoryDTO::from)
                        .toList()
                )
                .createdDate(car.getCreatedDate())
                .build();
    }
}
