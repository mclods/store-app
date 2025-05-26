package com.mclods.store_app.mappers;

import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.UserResponse;
import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.HashSet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {
    public User mapCreateUserRequestToUser(CreateUserRequest createUserRequest) {
       User user = new User(
               null,
               createUserRequest.getName(),
               createUserRequest.getEmail(),
               createUserRequest.getPassword(),
               new ArrayList<>(),
               new HashSet<>(),
               null,
               new HashSet<>()
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

    public abstract UserResponse mapUserToUserResponse(User user);
}
