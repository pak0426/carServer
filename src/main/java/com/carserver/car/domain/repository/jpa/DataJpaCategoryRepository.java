package com.carserver.car.domain.repository.jpa;

import com.carserver.car.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaCategoryRepository extends JpaRepository<Category, Long>  {
}
