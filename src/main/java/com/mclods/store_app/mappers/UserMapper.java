package com.mclods.store_app.mappers;

import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
import com.mclods.store_app.domain.dtos.user.PartialUpdateUserRequest;
import com.mclods.store_app.domain.dtos.user.UserResponse;
import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {
    public abstract Address map(CreateUserRequest.CreateUserAddress createUserAddress);

    public abstract Profile map(CreateUserRequest.CreateUserProfile createUserProfile);

    public abstract Address map(FullUpdateUserRequest.FullUpdateUserAddress fullUpdateUserAddress);

    public abstract Profile map(FullUpdateUserRequest.FullUpdateUserProfile fullUpdateUserProfile);

    public abstract Address map(PartialUpdateUserRequest.PartialUpdateUserAddress partialUpdateUserAddress);

    public abstract Profile map(PartialUpdateUserRequest.PartialUpdateUserProfile partialUpdateUserProfile);

    public abstract UserResponse map(User user);

    public abstract UserResponse.UserAddress map(Address address);

    public User map(CreateUserRequest createUserRequest) {
       User user = User.builder()
               .name(createUserRequest.getName())
               .email(createUserRequest.getEmail())
               .password(createUserRequest.getPassword())
               .build();

        // Address Mapping
        var userAddresses = Optional.ofNullable(createUserRequest.getAddresses());

        userAddresses.ifPresent(addressesToMap -> {
            for (var addressToMap : addressesToMap) {
                var mappedAddress = Optional.ofNullable(map(addressToMap));
                mappedAddress.ifPresent(user::addAddress);
            }
        });

        // Profile Mapping
        var mappedProfile = Optional.ofNullable(map(createUserRequest.getProfile()));
        mappedProfile.ifPresent(user::addProfile);

        return user;
    }

    public User map(FullUpdateUserRequest fullUpdateUserRequest) {
        User user = User.builder()
                .id(fullUpdateUserRequest.getId())
                .name(fullUpdateUserRequest.getName())
                .email(fullUpdateUserRequest.getEmail())
                .password(fullUpdateUserRequest.getPassword())
                .build();

        // Address Mapping
        var userAddresses = Optional.ofNullable(fullUpdateUserRequest.getAddresses());

        userAddresses.ifPresent(addressesToMap -> {
            for (var addressToMap : addressesToMap) {
                var mappedAddress = Optional.ofNullable(map(addressToMap));
                mappedAddress.ifPresent(user::addAddress);
            }
        });

        // Profile Mapping
        var mappedProfile = Optional.ofNullable(map(fullUpdateUserRequest.getProfile()));
        mappedProfile.ifPresent(user::addProfile);

        return user;
    }

    public User map(PartialUpdateUserRequest partialUpdateUserRequest) {
        User user = User.builder()
                .id(partialUpdateUserRequest.getId())
                .name(partialUpdateUserRequest.getName())
                .email(partialUpdateUserRequest.getEmail())
                .password(partialUpdateUserRequest.getPassword())
                .build();

        // Address Mapping
        var userAddresses = Optional.ofNullable(partialUpdateUserRequest.getAddresses());

        userAddresses.ifPresent(addressesToMap -> {
            for (var addressToMap : addressesToMap) {
                var mappedAddress = Optional.ofNullable(map(addressToMap));
                mappedAddress.ifPresent(user::addAddress);
            }
        });

        // Profile Mapping
        var mappedProfile = Optional.ofNullable(map(partialUpdateUserRequest.getProfile()));
        mappedProfile.ifPresent(user::addProfile);

        return user;
    }
}
