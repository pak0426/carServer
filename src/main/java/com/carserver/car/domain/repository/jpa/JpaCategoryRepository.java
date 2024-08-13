package com.carserver.car.domain.repository.jpa;

import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.entity.QCar;
import com.carserver.car.domain.entity.QCategory;
import com.carserver.car.domain.repository.CategoryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class JpaCategoryRepository implements CategoryRepository {

    private final JpaRepository<Category, Long> jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Category> findById(Long id) {
        return jpaRepository.findById(id);
    }

    public List<Category> findAllByIds(List<Long> ids) {
        QCategory category = QCategory.category;
        QCar car = QCar.car;

        return queryFactory
                .selectFrom(category)
                .leftJoin(category.cars, car).fetchJoin()
                .where(category.id.in(ids))
                .fetch();
    }

    @Override
    public List<Category> saveAll(List<Category> categories) {
        return jpaRepository.saveAll(categories);
    }
}
