package com.mclods.store_app.services;

public interface AddressService {
    boolean exists(Long id);
    boolean existsWithUserId(Long addressId, Long userId);
}
