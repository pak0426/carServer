package com.carserver.car.controller;

import com.carserver.car.dto.CarSearchCondition;
import com.carserver.car.dto.response.CarAvailabilityResponse;
import com.carserver.car.dto.request.SaveCarRequest;
import com.carserver.car.dto.response.CarWithCategoriesResponse;
import com.carserver.car.dto.response.SaveCarResponse;
import com.carserver.car.dto.request.UpdateCarRequest;
import com.carserver.car.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CarController {

    private final CarService carService;

    @PostMapping("/cars")
    public ResponseEntity<SaveCarResponse> save(@RequestBody @Valid SaveCarRequest request) {
        return ResponseEntity.ok(carService.save(request));
    }

    @PatchMapping("/cars/{id}")
    public ResponseEntity<SaveCarResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateCarRequest request) {
        return ResponseEntity.ok(carService.update(id, request));
    }

    @GetMapping("/cars/availability")
    public ResponseEntity<CarAvailabilityResponse> findCarAvailability(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(carService.findCarAvailability(ids));
    }

    @GetMapping("/cars")
    public ResponseEntity<CarWithCategoriesResponse> search(@ModelAttribute CarSearchCondition condition) {
        return ResponseEntity.ok(carService.search(condition));
    }
}
