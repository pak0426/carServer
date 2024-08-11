package com.carserver.config;

import com.carserver.car.domain.repository.CarRepository;
import com.carserver.car.domain.repository.jpa.DataJpaCarRepository;
import com.carserver.car.domain.repository.jpa.JpaCarRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public CarRepository carRepository(DataJpaCarRepository dataJpaCarRepository) {
        return new JpaCarRepository(dataJpaCarRepository);
    }
}
