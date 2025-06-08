package com.mclods.store_app.services;

import com.mclods.store_app.domain.entities.Address;

import java.util.List;

public interface AddressService {
    boolean exists(Long id);
    boolean existsWithUserId(Long addressId, Long userId);
    List<Address> findAllByUserId(Long userId);
}
