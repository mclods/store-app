package com.mclods.store_app.unit.mappers;

import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
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

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(MockitoExtension.class)
public class UserMapperTests {
    @Test
    @DisplayName("Test mapCreateUserRequestToUser generates the correct mapping")
    void testMapCreateUserRequestToUserGeneratesTheCorrectMapping()
    {
        CreateUserRequest.CreateUserAddress createUserAddressA = TestDataUtils.testCreateUserAddressA(),
                createUserAddressB = TestDataUtils.testCreateUserAddressB();
        CreateUserRequest.CreateUserProfile createUserProfile = TestDataUtils.testCreateUserProfileA();

        List<CreateUserRequest.CreateUserAddress> createUserAddresses = new ArrayList<>();
        createUserAddresses.add(createUserAddressA);
        createUserAddresses.add(createUserAddressB);

        Set<Integer> tagIds = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Long> productIds = new HashSet<>(Arrays.asList(1L, 2L, 3L));

        CreateUserRequest createUserRequest = new CreateUserRequest(
                "Solomon Adi",
                "solomom.islands@gz.com",
                "passforpass123",
                createUserAddresses,
                tagIds,
                createUserProfile,
                productIds
        );

        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        User mappedUser = userMapper.mapCreateUserRequestToUser(createUserRequest);

        // Assert User
        assertThat(mappedUser)
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly("Solomon Adi", "solomom.islands@gz.com", "passforpass123");

        // Assert Addresses
        assertThat(mappedUser.getAddresses()).hasSize(2);
        assertThat(mappedUser.getAddresses())
                .extracting(Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                "Ben Yehuda Alley",
                                "Tel Aviv - Jaffa",
                                "123-456",
                                "Israel"
                        ),
                        tuple(
                                "14/6 Reiser Micha",
                                "Lod",
                                "222-111",
                                "Israel"
                        )
                );

        // Assert Profile
        assertThat(mappedUser.getProfile())
                .extracting(Profile::getBio, Profile::getPhoneNumber,
                        Profile::getDateOfBirth, Profile::getLoyaltyPoints)
                .containsExactly(
                        "Hi I'm Camelot",
                        "564-231-411",
                        LocalDate.parse("2025-05-31"),
                        55
                );
    }

    @Test
    @DisplayName("Test mapFullUpdateUserRequestToUser generates the correct mapping")
    void testMapFullUpdateUserRequestToUserGeneratesTheCorrectMapping() {
        FullUpdateUserRequest.FullUpdateUserAddress fullUpdateUserAddress = TestDataUtils.testFullUpdateUserAddressA();
        FullUpdateUserRequest.FullUpdateUserProfile fullUpdateUserProfile = TestDataUtils.testFullUpdateUserProfileA();

        List<FullUpdateUserRequest.FullUpdateUserAddress> fullUpdateUserAddresses = new ArrayList<>();
        fullUpdateUserAddresses.add(fullUpdateUserAddress);

        Set<Integer> tagIds = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Long> productIds = new HashSet<>(Arrays.asList(1L, 2L, 3L));

        FullUpdateUserRequest fullUpdateUserRequest = new FullUpdateUserRequest(
                "Solomon Adi",
                "solomom.islands@gz.com",
                "passforpass123",
                fullUpdateUserAddresses,
                tagIds,
                fullUpdateUserProfile,
                productIds
        );

        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        User mappedUser = userMapper.mapFullUpdateUserRequestToUser(fullUpdateUserRequest);

        // Assert User
        assertThat(mappedUser)
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly("Solomon Adi", "solomom.islands@gz.com", "passforpass123");

        // Assert Addresses
        assertThat(mappedUser.getAddresses()).hasSize(1);
        assertThat(mappedUser.getAddresses())
                .extracting(Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                "Ben Yehuda Alley",
                                "Tel Aviv - Jaffa",
                                "123-456",
                                "Israel"
                        )
                );

        // Assert Profile
        assertThat(mappedUser.getProfile())
                .extracting(Profile::getBio, Profile::getPhoneNumber,
                        Profile::getDateOfBirth, Profile::getLoyaltyPoints)
                .containsExactly(
                        "Hi I'm Camelot",
                        "564-231-411",
                        LocalDate.parse("2025-05-31"),
                        55
                );
    }
}
