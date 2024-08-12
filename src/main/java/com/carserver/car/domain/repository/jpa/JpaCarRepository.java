package com.carserver.car.domain.repository.jpa;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.QCar;
import com.carserver.car.domain.repository.CarRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCarRepository implements CarRepository {

    private final JpaRepository<Car, Long> jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Car save(Car car) {
        return jpaRepository.save(car);
    }

    @Override
    public Optional<Car> findByIdWithCategories(Long id) {
        return Optional.ofNullable(
                queryFactory
                .selectFrom(QCar.car)
                .leftJoin(QCar.car.categories).fetchJoin()
                .where(QCar.car.id.eq(id))
                .fetchOne());
    }
}
