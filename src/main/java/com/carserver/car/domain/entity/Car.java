package com.carserver.car.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Positive
    private int productionYear;

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "car_category",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    public Car(String brand, String model, RentalStatus rentalStatus, List<Category> categories, int productionYear) {
        this(brand, model, rentalStatus, categories, productionYear, LocalDateTime.now(), LocalDateTime.now());
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

    public void update(Car car) {
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.rentalStatus = car.getRentalStatus();
        this.categories = car.getCategories();
        this.productionYear = car.getProductionYear();
        this.updatedDate = LocalDateTime.now();
    }

    public boolean isAvailable() {
        return this.getRentalStatus() == RentalStatus.AVAILABLE;
    }
}
