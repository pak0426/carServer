package com.carserver.car.dto;

import com.carserver.car.domain.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryWithCarsDTO {
    private Long id;
    private String name;
    private List<CarDTO> cars;

    public static CategoryWithCarsDTO from(Category category) {
        return CategoryWithCarsDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .cars(category.getCars().stream()
                        .map(CarDTO::from)
                        .toList())
                .build();
    }
}
