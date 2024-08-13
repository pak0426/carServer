package com.carserver.car.dto.response;

import com.carserver.car.dto.CarWithCategoriesDTO;

import java.util.List;

public record CarWithCategoriesResponse(List<CarWithCategoriesDTO> carWithCategoriesDTO) {
}
