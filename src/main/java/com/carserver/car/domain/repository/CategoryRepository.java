package com.carserver.car.domain.repository;

import com.carserver.car.domain.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findById(Long id);
    List<Category> findAllByIds(List<Long> ids);
    List<Category> saveAll(List<Category> categories);
}
