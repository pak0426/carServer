package com.carserver.car.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String name;

    private RentalStatus rentalStatus;

    @ManyToMany
    private List<Category> categories;

    private int productionYear;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
