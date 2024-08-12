package com.carserver.car.dto;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SaveCarRequest {
    @NotNull(message = "브랜드명을 입력해주세요.")
    private String brand;

    @NotNull(message = "모델명을 입력해주세요.")
    private String model;

    @NotNull(message = "카테고리 id를 입력해주세요.")
    private List<Long> categoryIds;

    @NotNull(message = "생산년도를 입력해주세요.")
    private int productionYear;

    public Car toEntity(List<Category> categories) {
        return new Car(brand, model, categories, productionYear);
    }
}
