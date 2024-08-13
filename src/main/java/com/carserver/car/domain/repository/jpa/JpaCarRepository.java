package com.carserver.car.domain.repository.jpa;

import com.carserver.car.domain.entity.Car;
import com.carserver.car.domain.entity.QCar;
import com.carserver.car.domain.repository.CarRepository;
import com.carserver.car.dto.CarSearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public List<Car> saveAll(List<Car> cars) {
        return jpaRepository.saveAll(cars);
    }

    @Override
    public Optional<Car> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Car> findByIdWithCategories(Long id) {
        QCar car = QCar.car;

        return Optional.ofNullable(
                queryFactory
                .selectFrom(car)
                .leftJoin(car.categories).fetchJoin()
                .where(car.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<Car> findAllByIds(List<Long> ids) {
        QCar car = QCar.car;

        return queryFactory
                .selectFrom(car)
                .where(car.id.in(ids))
                .fetch();
    }

    @Override
    public List<Car> findCarsByCondition(CarSearchCondition condition) {
        QCar car = QCar.car;

        return queryFactory
                .selectFrom(car)
                .leftJoin(car.categories).fetchJoin()
                .where(createWhereCondition(condition))
                .fetch();
    }

    private BooleanBuilder createWhereCondition(CarSearchCondition condition) {
        QCar car = QCar.car;
        BooleanBuilder builder = new BooleanBuilder();

        Optional.ofNullable(condition.getBrand())
                .filter(brand -> !brand.isEmpty())
                .ifPresent(brand -> builder.and(car.brand.startsWith(brand)));

        Optional.ofNullable(condition.getModel())
                .filter(model -> !model.isEmpty())
                .ifPresent(model -> builder.and(car.model.startsWith(model)));

        Optional.ofNullable(condition.getProductionYear())
                .ifPresent(productionYear -> builder.and(car.productionYear.eq(productionYear)));

        return builder;
    }
}
