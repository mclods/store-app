package com.mclods.store_app.services.impl;

import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.exceptions.AddressHasMissingFieldsException;
import com.mclods.store_app.exceptions.UserNotFoundException;
import com.mclods.store_app.repositories.UserRepository;
import com.mclods.store_app.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User fullUpdateUser(Long id, User userToUpdate) throws UserNotFoundException {
        userToUpdate.setId(id);

        User existingUser = userRepository.findUser(id).orElseThrow(() -> new UserNotFoundException(id));

        // Prepare addresses for merge
        List<Long> existingUserAddressIds = existingUser.getAddresses()
                .stream().map(Address::getId).toList();

        if(!userToUpdate.getAddresses().isEmpty()) {
            userToUpdate.getAddresses().forEach(address -> {
                Optional.ofNullable(address.getId()).ifPresent(addressId -> {
                    if(!existingUserAddressIds.contains(addressId)) {
                        address.setId(null);
                    }
                });
            });
        }

        // Prepare profile for merge
        boolean existingUserHasProfile = Optional.ofNullable(existingUser.getProfile()).isPresent();

        Optional.ofNullable(userToUpdate.getProfile()).ifPresent(profile -> {
            if(existingUserHasProfile) {
                profile.setId(id);
            } else {
                profile.setId(null);
            }
        });

        return userRepository.save(userToUpdate);
    }

    @Override
    public User partialUpdateUser(Long id, User userToUpdate) throws UserNotFoundException {
        User existingUser = userRepository.findUser(id).orElseThrow(() -> new UserNotFoundException(id));

        // Merge basic fields
        Optional.ofNullable(userToUpdate.getName()).ifPresent(existingUser::setName);
        Optional.ofNullable(userToUpdate.getEmail()).ifPresent(existingUser::setEmail);
        Optional.ofNullable(userToUpdate.getPassword()).ifPresent(existingUser::setPassword);

        // Prepare addresses for merge
        List<Address> existingUserAddresses = existingUser.getAddresses();

        if(!userToUpdate.getAddresses().isEmpty()) {
            userToUpdate.getAddresses().forEach(addressToUpdate -> {
                Optional<Address> existingUserAddress = existingUserAddresses.stream()
                        .filter(a -> a.getId() != null &&
                                a.getId().equals(addressToUpdate.getId()))
                        .findFirst();


                existingUserAddress.ifPresentOrElse(existingAddress -> {
                    Optional.ofNullable(addressToUpdate.getStreet()).ifPresent(existingAddress::setStreet);
                    Optional.ofNullable(addressToUpdate.getCity()).ifPresent(existingAddress::setCity);
                    Optional.ofNullable(addressToUpdate.getZip()).ifPresent(existingAddress::setZip);
                    Optional.ofNullable(addressToUpdate.getState()).ifPresent(existingAddress::setState);
                }, () -> {
                    addressToUpdate.setId(null);

                    if(addressToUpdate.isValid()) {
                        existingUser.addAddress(addressToUpdate);
                    } else {
                        throw new RuntimeException(new AddressHasMissingFieldsException(addressToUpdate));
                    }
                });
            });
        }

        // Prepare profile for merge
        Optional<Profile> existingUserProfile = Optional.ofNullable(existingUser.getProfile());

        Optional.ofNullable(userToUpdate.getProfile()).ifPresent(profileToUpdate -> {
            existingUserProfile.ifPresentOrElse(existingProfile -> {
                Optional.ofNullable(profileToUpdate.getBio()).ifPresent(existingProfile::setBio);
                Optional.ofNullable(profileToUpdate.getPhoneNumber()).ifPresent(existingProfile::setPhoneNumber);
                Optional.ofNullable(profileToUpdate.getDateOfBirth()).ifPresent(existingProfile::setDateOfBirth);
                Optional.ofNullable(profileToUpdate.getLoyaltyPoints()).ifPresent(existingProfile::setLoyaltyPoints);
            }, () -> {
                profileToUpdate.setId(null);
                existingUser.addProfile(profileToUpdate);
            });
        });

        return userRepository.save(existingUser);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return userRepository.findUser(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userRepository.findAllUsers());
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
