package com.carserver.car.dto.response;

import com.carserver.car.dto.CarAvailabilityDTO;

import java.util.List;

public record CarAvailabilityResponse(List<CarAvailabilityDTO> carAvailabilityDTOS) {
}
