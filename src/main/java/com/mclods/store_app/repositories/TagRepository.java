package com.mclods.store_app.repositories;

import com.mclods.store_app.domain.entities.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
}
