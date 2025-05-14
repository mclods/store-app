package com.mclods.store_app.repositories;

import com.mclods.store_app.domain.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Short> {
}
