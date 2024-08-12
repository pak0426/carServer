package com.carserver.car.controller;

import com.carserver.car.dto.SaveCarRequest;
import com.carserver.car.dto.SaveCarResponse;
import com.carserver.car.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CarController {

    private final CarService carService;

    @PostMapping("/cars")
    public ResponseEntity<SaveCarResponse> save(@RequestBody @Valid SaveCarRequest request) {
        return ResponseEntity.ok(carService.save(request));
    }
}
