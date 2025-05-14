package com.mclods.store_app.repositories;

import com.mclods.store_app.domain.entities.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
