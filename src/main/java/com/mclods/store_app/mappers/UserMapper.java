package com.mclods.store_app.mappers;

import com.mclods.store_app.domain.dtos.common.AddressDto;
import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.UserResponse;
import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private ProfileMapper profileMapper;

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
       for (AddressDto addressDto : createUserRequest.getAddresses()) {
           Address address = addressMapper.mapAddressDtoToAddress(addressDto);

           if(address != null) {
               user.addAddress(address);
           }
       }

        // Profile
        Profile profile = profileMapper.mapProfileDtoToProfile(createUserRequest.getProfile());

        if(profile != null) {
           user.addProfile(profile);
        }

        // Tags

        // Wishlist

        return user;
    }

    public abstract UserResponse mapUserToUserResponse(User user);
}
