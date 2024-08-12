package com.carserver.car.domain.repository.jpa;

import com.carserver.car.domain.entity.Category;
import com.carserver.car.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class JpaCategoryRepository implements CategoryRepository {

    private final JpaRepository<Category, Long> jpaRepository;

    @Override
    public Optional<Category> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Category> findAllByIds(List<Long> ids) {
        return jpaRepository.findAllById(ids);
    }

    @Override
    public List<Category> saveAll(List<Category> categories) {
        return jpaRepository.saveAll(categories);
    }
}
