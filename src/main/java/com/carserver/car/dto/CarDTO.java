package com.carserver.car.dto;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.RentalStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarDTO {
    private Long id;
    private String brand;
    private String model;
    private RentalStatus rentalStatus;
    private int productionYear;

    public static CarDTO from(Car car) {
        return CarDTO.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .rentalStatus(car.getRentalStatus())
                .productionYear(car.getProductionYear())
                .build();
    }
}
