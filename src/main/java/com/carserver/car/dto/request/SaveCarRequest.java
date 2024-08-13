package com.carserver.car.dto.request;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.entity.RentalStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, message = "적어도 하나의 카테고리 id를 입력해주세요.")
    private List<Long> categoryIds;

    @Positive(message = "생산년도를 올바르게 입력해주세요.")
    private int productionYear;

    public Car toEntity(List<Category> categories) {
        return new Car(brand, model, RentalStatus.AVAILABLE, categories, productionYear);
    }
}
