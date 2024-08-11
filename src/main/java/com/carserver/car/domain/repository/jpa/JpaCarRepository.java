package com.carserver.car.domain.repository.jpa;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaCarRepository implements CarRepository {

    private final JpaRepository<Car, Long> jpaRepository;

    @Override
    public Car save(Car car) {
        return jpaRepository.save(car);
    }
}
