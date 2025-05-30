package com.mclods.store_app.mappers;

import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
import com.mclods.store_app.domain.dtos.user.UserResponse;
import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {
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

       // Address
       for (CreateUserRequest.CreateUserAddress createUserAddress : createUserRequest.getAddresses()) {
           Address address = mapCreateUserAddressToAddress(createUserAddress);

           if(address != null) {
               user.addAddress(address);
           }
       }

        // Profile
        Profile profile = mapCreateUserProfileToProfile(createUserRequest.getProfile());

        if(profile != null) {
           user.addProfile(profile);
        }

        // Tags

        // Wishlist

        return user;
    }

    public abstract Address mapCreateUserAddressToAddress(CreateUserRequest.CreateUserAddress createUserAddress);

    public abstract Profile mapCreateUserProfileToProfile(CreateUserRequest.CreateUserProfile createUserProfile);

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

        // Address
        for (FullUpdateUserRequest.FullUpdateUserAddress fullUpdateUserAddress : fullUpdateUserRequest.getAddresses()) {
            Address address = mapFullUpdateUserAddressToAddress(fullUpdateUserAddress);

            if(address != null) {
                user.addAddress(address);
            }
        }

        // Profile
        Profile profile = mapFullUpdateUserProfileToProfile(fullUpdateUserRequest.getProfile());

        if(profile != null) {
            user.addProfile(profile);
        }

        // Tags

        // Wishlist

        return user;
    }

    public abstract Address mapFullUpdateUserAddressToAddress(FullUpdateUserRequest.FullUpdateUserAddress fullUpdateUserAddress);

    public abstract Profile mapFullUpdateUserProfileToProfile(FullUpdateUserRequest.FullUpdateUserProfile fullUpdateUserProfile);

    public abstract UserResponse mapUserToUserResponse(User user);
}
