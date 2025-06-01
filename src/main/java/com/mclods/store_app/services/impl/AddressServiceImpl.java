package com.mclods.store_app.services.impl;

import com.mclods.store_app.repositories.AddressRepository;
import com.mclods.store_app.services.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public boolean exists(Long id) {
        return addressRepository.existsById(id);
    }

    @Override
    public boolean existsWithUserId(Long addressId, Long userId) {
        return addressRepository.existsByIdAndUserId(addressId, userId);
    }
}
