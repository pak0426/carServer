package com.carserver.car.dto;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.RentalStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarAvailabilityDTO {
    private Long id;
    private boolean available;
    private RentalStatus rentalStatus;

    public static CarAvailabilityDTO from(Car car) {
        return CarAvailabilityDTO.builder()
                .id(car.getId())
                .available(car.isAvailable())
                .rentalStatus(car.getRentalStatus())
                .build();
    }
}
