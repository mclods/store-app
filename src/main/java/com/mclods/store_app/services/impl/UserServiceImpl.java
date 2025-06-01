package com.mclods.store_app.services.impl;

import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.repositories.UserRepository;
import com.mclods.store_app.services.AddressService;
import com.mclods.store_app.services.ProfileService;
import com.mclods.store_app.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final AddressService addressService;

    public UserServiceImpl(UserRepository userRepository, ProfileService profileService, AddressService addressService) {
        this.userRepository = userRepository;
        this.profileService = profileService;
        this.addressService = addressService;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User fullUpdateUser(Long id, User user) {
        user.setId(id);

        // Clear Invalid Address Ids
        Optional.ofNullable(user.getAddresses()).ifPresent(addresses -> {
            for (Address address : addresses) {
                Optional.ofNullable(address.getId()).ifPresent(addressId -> {
                    if(!addressService.existsWithUserId(addressId, id)) {
                        address.setId(null);
                    }
                });
            }
        });

        Optional.ofNullable(user.getProfile()).ifPresent(profile -> {
            if(profileService.exists(id)) {
                profile.setId(id);
            }
        });

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }
}
