package com.mclods.store_app.repositories;

import com.mclods.store_app.domain.entities.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
    boolean existsByIdAndUserId(Long addressId, Long userId);
}
