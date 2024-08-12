package com.carserver.car.domain.repository.jpa;

import com.carserver.car.domain.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaCarRepository extends JpaRepository<Car, Long> {
}