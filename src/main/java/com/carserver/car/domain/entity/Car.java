package com.carserver.car.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RentalStatus rentalStatus;

    @Min(value = 1880, message = "생산 연도는 1880 이상이어야 합니다.")
    private int productionYear;

    @NotEmpty(message = "적어도 하나의 카테고리가 필요합니다.")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "car_category",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    public Car(String brand, String model, List<Category> categories, int productionYear) {
        this(brand, model, RentalStatus.AVAILABLE, categories, productionYear, LocalDateTime.now(), LocalDateTime.now());
    }

    @Builder
    public Car(String brand, String model, RentalStatus rentalStatus, List<Category> categories, int productionYear, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.brand = brand;
        this.model = model;
        this.rentalStatus = rentalStatus;
        this.categories = categories;
        this.productionYear = productionYear;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void addCategories(List<Category> categories) {
        for (Category category : categories) {
            this.categories.add(category);
            category.getCars().add(this);
        }

    }
}
