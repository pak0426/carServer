package com.carserver.car.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CarSearchCondition {
    private String brand;
    private String model;
    private Integer productionYear;
}
