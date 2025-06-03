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

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {
    public abstract Address mapCreateUserAddressToAddress(CreateUserRequest.CreateUserAddress createUserAddress);

    public abstract Profile mapCreateUserProfileToProfile(CreateUserRequest.CreateUserProfile createUserProfile);

    public abstract Address mapFullUpdateUserAddressToAddress(FullUpdateUserRequest.FullUpdateUserAddress fullUpdateUserAddress);

    public abstract Profile mapFullUpdateUserProfileToProfile(FullUpdateUserRequest.FullUpdateUserProfile fullUpdateUserProfile);

    public abstract Address mapPartialUpdateUserAddressToAddress(PartialUpdateUserRequest.PartialUpdateUserAddress partialUpdateUserAddress);

    public abstract Profile mapPartialUpdateUserProfileToProfile(PartialUpdateUserRequest.PartialUpdateUserProfile partialUpdateUserProfile);

    public abstract UserResponse mapUserToUserResponse(User user);

    public User mapCreateUserRequestToUser(CreateUserRequest createUserRequest) {
       User user = new User(
               null,
               createUserRequest.getName(),
               createUserRequest.getEmail(),
               createUserRequest.getPassword(),
               null,
               null,
               null,
               null
       );

       // Address Mapping
        if(createUserRequest.getAddresses() != null) {
            for (CreateUserRequest.CreateUserAddress createUserAddress : createUserRequest.getAddresses()) {
                Address address = mapCreateUserAddressToAddress(createUserAddress);

                if(address != null) {
                    user.addAddress(address);
                }
            }
        }

        // Profile Mapping
        Profile profile = mapCreateUserProfileToProfile(createUserRequest.getProfile());
        if(profile != null) {
           user.addProfile(profile);
        }

        // TODO: Tags

        // TODO: Wishlist

        return user;
    }

    public User mapFullUpdateUserRequestToUser(FullUpdateUserRequest fullUpdateUserRequest) {
        User user = new User(
                null,
                fullUpdateUserRequest.getName(),
                fullUpdateUserRequest.getEmail(),
                fullUpdateUserRequest.getPassword(),
                null,
                null,
                null,
                null
        );

        // Address Mapping
        if(fullUpdateUserRequest.getAddresses() != null) {
            for (FullUpdateUserRequest.FullUpdateUserAddress fullUpdateUserAddress : fullUpdateUserRequest.getAddresses()) {
                Address address = mapFullUpdateUserAddressToAddress(fullUpdateUserAddress);

                if(address != null) {
                    user.addAddress(address);
                }
            }
        }

        // Profile Mapping
        Profile profile = mapFullUpdateUserProfileToProfile(fullUpdateUserRequest.getProfile());
        if(profile != null) {
            user.addProfile(profile);
        }

        // TODO: Tags

        // TODO: Wishlist

        return user;
    }

    public User mapPartialUpdateUserRequestToUser(PartialUpdateUserRequest partialUpdateUserRequest) {
        User user = new User(
                null,
                partialUpdateUserRequest.getName(),
                partialUpdateUserRequest.getEmail(),
                partialUpdateUserRequest.getPassword(),
                null,
                null,
                null,
                null
        );

        // Address Mapping
        if(partialUpdateUserRequest.getAddresses() != null) {
            for (PartialUpdateUserRequest.PartialUpdateUserAddress partialUpdateUserAddress : partialUpdateUserRequest.getAddresses()) {
                Address address = mapPartialUpdateUserAddressToAddress(partialUpdateUserAddress);

                if(address != null) {
                    user.addAddress(address);
                }
            }
        }

        // Profile Mapping
        Profile profile = mapPartialUpdateUserProfileToProfile(partialUpdateUserRequest.getProfile());
        if(profile != null) {
            user.addProfile(profile);
        }

        // TODO: Tags

        // TODO: Wishlist

        return user;
    }
}
