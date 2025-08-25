package com.mclods.store_app.utils;

import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
import com.mclods.store_app.domain.dtos.user.PartialUpdateUserRequest;
import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;

import java.time.LocalDate;
import java.util.*;

public class TestDataUtils {
    public static User testUserA() {
        return User.builder()
                .name("Michael Akrawi")
                .email("michael.akrawi@email.com")
                .password("michael123")
                .build();
    }

    public static User testUserB() {
        return User.builder()
                .name("Ari Schwartz")
                .email("ari.schwartz@mailman.com")
                .password("49ari")
                .build();
    }

    public static User partiallyUpdatedUserA() {
        return User.builder()
                .name("Chemical Michael")
                .build();
    }

    public static User partiallyUpdatedUserB() {
        return User.builder()
                .name("Stormy Night")
                .email("fortnight.storm@mail.com")
                .build();
    }

    public static Address testAddressA() {
        return Address.builder()
                .street("Ben Yehuda Alley")
                .city("Tel Aviv - Jaffa")
                .zip("123-456")
                .state("Israel")
                .build();
    }

    public static Address testAddressB() {
        return Address.builder()
                .street("16 Yafo Street")
                .city("Jerusalem")
                .zip("765-210")
                .state("Israel")
                .build();
    }

    public static Address testAddressC() {
        return Address.builder()
                .street("4 Hamatechet")
                .city("Hadera")
                .zip("541-000")
                .state("Israel")
                .build();
    }

    public static Profile testProfileA() {
        return Profile.builder()
                .bio("Hi I'm Michael")
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.parse("2025-05-27"))
                .loyaltyPoints(25)
                .build();
    }

    public static Profile testProfileB() {
        return Profile.builder()
                .bio("Ari is an old man.")
                .phoneNumber("8761239810")
                .dateOfBirth(LocalDate.parse("2025-05-30"))
                .loyaltyPoints(30)
                .build();
    }

    public static User testUserWithAddressAndProfileA() {
        User user = testUserA();

        user.addAddress(testAddressA());
        user.addAddress(testAddressB());

        user.addProfile(testProfileA());

        return user;
    }

    public static User testExistingUserWithAddressAndProfileA() {
        User user = testUserA();
        user.setId(1L);

        user.addAddress(testAddressA());
        user.getAddresses().get(0).setId(1L);

        user.addAddress(testAddressB());
        user.getAddresses().get(1).setId(2L);

        user.addProfile(testProfileA());
        user.getProfile().setId(1L);

        return user;
    }

    public static User testUserWithAddressAndProfileB() {
        User user = testUserB();

        user.addAddress(testAddressC());
        user.addProfile(testProfileB());

        return user;
    }

    public static User testPartiallyUpdatedUserWithAddressAndProfileA() {
        User user = partiallyUpdatedUserA();

        user.addAddress(testAddressA());
        user.addAddress(testAddressB());

        user.addProfile(testProfileA());

        return user;
    }

    public static User testPartiallyUpdatedUserWithAddressAndProfileB() {
        User user = partiallyUpdatedUserB();

        user.addAddress(testAddressA());
        user.addProfile(testProfileA());

        return user;
    }

    public static CreateUserRequest testCreateUserRequestA() {
        return CreateUserRequest.builder()
                .name("Solomon Adi")
                .email("solomom.islands@gz.com")
                .password("passforpass123")
                .build();
    }

    public static CreateUserRequest testCreateUserRequestWithAddressAndProfileA() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .name("Solomon Adi")
                .email("solomom.islands@gz.com")
                .password("passforpass123")
                .build();

        createUserRequest.setAddresses(Arrays.asList(testCreateUserAddressA(), testCreateUserAddressB()));
        createUserRequest.setProfile(testCreateUserProfileA());

        return createUserRequest;
    }

    public static CreateUserRequest.CreateUserAddress testCreateUserAddressA() {
        return new CreateUserRequest.CreateUserAddress(
                "42 Montefiori",
                "Tel Aviv",
                "681-231",
                "Israel"
        );
    }

    public static CreateUserRequest.CreateUserAddress testCreateUserAddressB() {
        return new CreateUserRequest.CreateUserAddress(
                "14/6 Reiser Micha",
                "Lod",
                "222-111",
                "Israel"
        );
    }

    public static CreateUserRequest.CreateUserProfile testCreateUserProfileA() {
        return new CreateUserRequest.CreateUserProfile(
                "Hi I'm Camelot",
                "564-231-411",
                LocalDate.parse("2025-05-31"),
                55
        );
    }

    public static FullUpdateUserRequest testFullUpdateUserRequestA() {
        return FullUpdateUserRequest.builder()
                .name("Melamed Shai")
                .email("shai.melamed@gmail.com")
                .password("shai.starpass123")
                .build();
    }

    public static FullUpdateUserRequest testFullUpdateUserRequestWithAddressAndProfileA() {
        FullUpdateUserRequest fullUpdateUserRequest = FullUpdateUserRequest.builder()
                .name("Melamed Shai")
                .email("shai.melamed@gmail.com")
                .password("shai.starpass123")
                .build();

        fullUpdateUserRequest.setAddresses(List.of(testFullUpdateUserAddressA()));
        fullUpdateUserRequest.setProfile(testFullUpdateUserProfileA());

        return fullUpdateUserRequest;
    }

    public static FullUpdateUserRequest.FullUpdateUserAddress testFullUpdateUserAddressA() {
        return new FullUpdateUserRequest.FullUpdateUserAddress(
                10L,
                "11 Pduiim",
                "Hod Hasharon",
                "222-000",
                "Israel"
        );
    }

    public static FullUpdateUserRequest.FullUpdateUserAddress testFullUpdateUserAddressB() {
        return new FullUpdateUserRequest.FullUpdateUserAddress(
                12L,
                "Pob 13078 61130",
                "Tel Aviv",
                "311-912",
                "Israel"
        );
    }

    public static FullUpdateUserRequest.FullUpdateUserProfile testFullUpdateUserProfileA() {
        return new FullUpdateUserRequest.FullUpdateUserProfile(
                "Jamie Olivier",
                "564-231-411",
                LocalDate.parse("2025-05-31"),
                55
        );
    }

    public static PartialUpdateUserRequest testPartialUpdateUserRequestA() {
        return PartialUpdateUserRequest.builder()
                .name("Crazy Toon")
                .build();
    }

    public static PartialUpdateUserRequest.PartialUpdateUserAddress testPartialUpdateUserAddressA() {
        return new PartialUpdateUserRequest.PartialUpdateUserAddress(
                10L,
                "17 Haoman",
                "Jerusalem",
                "2311-1",
                "Israel"
        );
    }

    public static PartialUpdateUserRequest.PartialUpdateUserAddress testPartialUpdateUserAddressB() {
        return new PartialUpdateUserRequest.PartialUpdateUserAddress(
                20L,
                "142 Iben Gabirol",
                null,
                null,
                "Israel"
        );
    }

    public static PartialUpdateUserRequest.PartialUpdateUserProfile testPartialUpdateUserProfileA() {
        return new PartialUpdateUserRequest.PartialUpdateUserProfile(
                "I'm an Ingenious Person",
                null,
                LocalDate.parse("2025-06-02"),
                21
        );
    }

    public static PartialUpdateUserRequest testPartialUpdateUserRequestWithAddressAndProfileA() {
        PartialUpdateUserRequest partialUpdateUserRequest = testPartialUpdateUserRequestA();

        partialUpdateUserRequest.setAddresses(List.of(testPartialUpdateUserAddressA()));
        partialUpdateUserRequest.setProfile(testPartialUpdateUserProfileA());

        return partialUpdateUserRequest;
    }
}
