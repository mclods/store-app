package com.mclods.store_app.unit.mappers;

import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
import com.mclods.store_app.domain.dtos.user.PartialUpdateUserRequest;
import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.mappers.UserMapper;
import com.mclods.store_app.utils.TestDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(MockitoExtension.class)
public class UserMapperTests {
    @Test
    @DisplayName("Test CreateUserRequest to User gets mapped")
    void testCreateUserRequestToUserGetsMapped()
    {
        CreateUserRequest createUserRequest = TestDataUtils.testCreateUserRequestWithAddressAndProfileA();

        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        User mappedUser = userMapper.map(createUserRequest);

        // Assert User
        assertThat(mappedUser)
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly(createUserRequest.getName(), createUserRequest.getEmail(), createUserRequest.getPassword());

        // Assert Addresses
        assertThat(mappedUser.getAddresses()).hasSize(2);
        assertThat(mappedUser.getAddresses())
                .extracting(Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                createUserRequest.getAddresses().get(0).getStreet(),
                                createUserRequest.getAddresses().get(0).getCity(),
                                createUserRequest.getAddresses().get(0).getZip(),
                                createUserRequest.getAddresses().get(0).getState()
                        ),
                        tuple(
                                createUserRequest.getAddresses().get(1).getStreet(),
                                createUserRequest.getAddresses().get(1).getCity(),
                                createUserRequest.getAddresses().get(1).getZip(),
                                createUserRequest.getAddresses().get(1).getState()
                        )
                );

        // Assert Profile
        assertThat(mappedUser.getProfile())
                .extracting(Profile::getBio, Profile::getPhoneNumber,
                        Profile::getDateOfBirth, Profile::getLoyaltyPoints)
                .containsExactly(
                        createUserRequest.getProfile().getBio(),
                        createUserRequest.getProfile().getPhoneNumber(),
                        createUserRequest.getProfile().getDateOfBirth(),
                        createUserRequest.getProfile().getLoyaltyPoints()
                );
    }

    @Test
    @DisplayName("Test FullUpdateUserRequest to User gets mapped")
    void testFullUpdateUserRequestToUserGetsMapped() {
        FullUpdateUserRequest fullUpdateUserRequest = TestDataUtils.testFullUpdateUserRequestWithAddressAndProfileA();

        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        User mappedUser = userMapper.map(fullUpdateUserRequest);

        // Assert User
        assertThat(mappedUser)
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly(fullUpdateUserRequest.getName(), fullUpdateUserRequest.getEmail(), fullUpdateUserRequest.getPassword());

        // Assert Addresses
        assertThat(mappedUser.getAddresses()).hasSize(1);
        assertThat(mappedUser.getAddresses())
                .extracting(Address::getId, Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                fullUpdateUserRequest.getAddresses().get(0).getId(),
                                fullUpdateUserRequest.getAddresses().get(0).getStreet(),
                                fullUpdateUserRequest.getAddresses().get(0).getCity(),
                                fullUpdateUserRequest.getAddresses().get(0).getZip(),
                                fullUpdateUserRequest.getAddresses().get(0).getState()
                        )
                );

        // Assert Profile
        assertThat(mappedUser.getProfile())
                .extracting(Profile::getBio, Profile::getPhoneNumber,
                        Profile::getDateOfBirth, Profile::getLoyaltyPoints)
                .containsExactly(
                        fullUpdateUserRequest.getProfile().getBio(),
                        fullUpdateUserRequest.getProfile().getPhoneNumber(),
                        fullUpdateUserRequest.getProfile().getDateOfBirth(),
                        fullUpdateUserRequest.getProfile().getLoyaltyPoints()
                );
    }

    @Test
    @DisplayName("Test PartialUpdateUserRequest to User gets mapped")
    void testPartialUpdateUserRequestToUserGetsMapped() {
        PartialUpdateUserRequest partialUpdateUserRequest = TestDataUtils.testPartialUpdateUserRequestWithAddressAndProfileA();

        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        User mappedUser = userMapper.map(partialUpdateUserRequest);

        // Assert User
        assertThat(mappedUser)
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly(partialUpdateUserRequest.getName(), partialUpdateUserRequest.getEmail(), partialUpdateUserRequest.getPassword());

        // Assert Addresses
        assertThat(mappedUser.getAddresses()).hasSize(1);
        assertThat(mappedUser.getAddresses())
                .extracting(Address::getId, Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                partialUpdateUserRequest.getAddresses().get(0).getId(),
                                partialUpdateUserRequest.getAddresses().get(0).getStreet(),
                                partialUpdateUserRequest.getAddresses().get(0).getCity(),
                                partialUpdateUserRequest.getAddresses().get(0).getZip(),
                                partialUpdateUserRequest.getAddresses().get(0).getState()
                        )
                );

        // Assert Profile
        assertThat(mappedUser.getProfile())
                .extracting(Profile::getBio, Profile::getPhoneNumber,
                        Profile::getDateOfBirth, Profile::getLoyaltyPoints)
                .containsExactly(
                        partialUpdateUserRequest.getProfile().getBio(),
                        partialUpdateUserRequest.getProfile().getPhoneNumber(),
                        partialUpdateUserRequest.getProfile().getDateOfBirth(),
                        partialUpdateUserRequest.getProfile().getLoyaltyPoints()
                );
    }
}
