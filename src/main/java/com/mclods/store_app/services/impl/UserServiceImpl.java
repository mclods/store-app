package com.mclods.store_app.services.impl;

import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.exceptions.AddressHasMissingFieldsException;
import com.mclods.store_app.exceptions.UserNotFoundException;
import com.mclods.store_app.repositories.UserRepository;
import com.mclods.store_app.services.AddressService;
import com.mclods.store_app.services.ProfileService;
import com.mclods.store_app.services.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final ProfileService profileService;

    public UserServiceImpl(UserRepository userRepository, AddressService addressService, ProfileService profileService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.profileService = profileService;
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
                    if (!addressService.existsWithUserId(addressId, id)) {
                        address.setId(null);
                    }
                });
            }
        });

        Optional.ofNullable(user.getProfile()).ifPresent(profile -> {
            if (profileService.exists(id)) {
                profile.setId(id);
            } else {
                profile.setId(null);
            }
        });

        return userRepository.save(user);
    }

    @Override
    public User partialUpdateUser(Long id, User user) throws UserNotFoundException, AddressHasMissingFieldsException {
        try {
            user.setId(id);

            return userRepository.findById(id).map(existingUser -> {
                Optional.ofNullable(user.getName()).ifPresent(existingUser::setName);
                Optional.ofNullable(user.getEmail()).ifPresent(existingUser::setEmail);
                Optional.ofNullable(user.getPassword()).ifPresent(existingUser::setPassword);

                // Update Addresses
                Optional.ofNullable(user.getAddresses()).ifPresent(addressesToUpdate -> {
                    for (Address addressToUpdate : addressesToUpdate) {
                        Optional.ofNullable(addressToUpdate.getId()).ifPresentOrElse(addressId ->
                            existingUser.findAddress(addressToUpdate).ifPresentOrElse(existingAddress -> {
                                Optional.ofNullable(addressToUpdate.getStreet())
                                        .ifPresent(existingAddress::setStreet);
                                Optional.ofNullable(addressToUpdate.getCity())
                                        .ifPresent(existingAddress::setCity);
                                Optional.ofNullable(addressToUpdate.getZip())
                                        .ifPresent(existingAddress::setZip);
                                Optional.ofNullable(addressToUpdate.getState())
                                        .ifPresent(existingAddress::setState);
                            }, () -> {
                                if (addressToUpdate.isValid()) {
                                    addressToUpdate.setId(null);
                                    existingUser.addAddress(addressToUpdate);
                                } else {
                                    throw new RuntimeException(new AddressHasMissingFieldsException(addressToUpdate));
                                }
                            })
                    , () -> {
                        if (addressToUpdate.isValid()) {
                            existingUser.addAddress(addressToUpdate);
                        } else {
                            throw new RuntimeException(new AddressHasMissingFieldsException(addressToUpdate));
                        }
                    });
                    }
                });

                // Update Profile
                Optional.ofNullable(user.getProfile()).ifPresent(profileToUpdate ->
                    Optional.ofNullable(existingUser.getProfile()).ifPresentOrElse(existingProfile -> {
                        profileToUpdate.setId(id);

                        Optional.ofNullable(profileToUpdate.getBio())
                                .ifPresent(existingProfile::setBio);
                        Optional.ofNullable(profileToUpdate.getPhoneNumber())
                                .ifPresent(existingProfile::setPhoneNumber);
                        Optional.ofNullable(profileToUpdate.getDateOfBirth())
                                .ifPresent(existingProfile::setDateOfBirth);
                        Optional.ofNullable(profileToUpdate.getLoyaltyPoints())
                                .ifPresent(existingProfile::setLoyaltyPoints);
                    }, () -> {
                        profileToUpdate.setId(null);
                        existingUser.addProfile(profileToUpdate);
                    })
                );

                return userRepository.save(existingUser);
            }).orElseThrow(() -> new UserNotFoundException(id));
        } catch (RuntimeException ex) {
            if (ex.getCause() instanceof AddressHasMissingFieldsException) {
                throw (AddressHasMissingFieldsException) ex.getCause();
            } else {
                throw ex;
            }
        }
    }

    @Override
    public Optional<User> findOne(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        List<User> foundUsers = new ArrayList<>();
        userRepository.findAll().forEach(foundUsers::add);
        return foundUsers;
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }
}
