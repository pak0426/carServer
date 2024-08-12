package com.carserver.config;

import com.carserver.car.domain.repository.CarRepository;
import com.carserver.car.domain.repository.CategoryRepository;
import com.carserver.car.domain.repository.jpa.DataJpaCarRepository;
import com.carserver.car.domain.repository.jpa.DataJpaCategoryRepository;
import com.carserver.car.domain.repository.jpa.JpaCarRepository;
import com.carserver.car.domain.repository.jpa.JpaCategoryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public CarRepository carRepository(DataJpaCarRepository dataJpaCarRepository, JPAQueryFactory queryFactory) {
        return new JpaCarRepository(dataJpaCarRepository, queryFactory);
    }

    @Bean
    public CategoryRepository categoryRepository(DataJpaCategoryRepository dataJpaCategoryRepository) {
        return new JpaCategoryRepository(dataJpaCategoryRepository);
    }
}
