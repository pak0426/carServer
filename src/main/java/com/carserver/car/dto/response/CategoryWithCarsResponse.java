package com.carserver.car.dto.response;

import com.carserver.car.dto.CategoryWithCarsDTO;

import java.util.List;

public record CategoryWithCarsResponse(List<CategoryWithCarsDTO> categoryWithCarsDTOS) {
}
