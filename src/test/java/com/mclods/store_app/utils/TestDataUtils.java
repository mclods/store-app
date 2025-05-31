package com.mclods.store_app.utils;

import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;

import java.time.LocalDate;
import java.util.*;

public class TestDataUtils {
    public static User testUserA() {
        return new User(
                null,
                "Michael Akrawi",
                "michael.akrawi@email.com",
                "michael123",
                null,
                null,
                null,
                null
        );
    }

    public static User testUserB() {
        return new User(
                null,
                "Ari Schwartz",
                "ari.schwartz@mailman.com",
                "49ari",
                null,
                null,
                null,
                null
        );
    }

    public static Address testAddressA() {
        return new Address(
                null,
                "Ben Yehuda Alley",
                "Tel Aviv - Jaffa",
                "123-456",
                "Israel",
                null
        );
    }

    public static Address testAddressB() {
        return new Address(
                null,
                "16 Yafo Street",
                "Jerusalem",
                "765-210",
                "Israel",
                null
        );
    }

    public static Address testAddressC() {
        return new Address(
                null,
                "4 Hamatechet",
                "Hadera",
                "541-000",
                "Israel",
                null
        );
    }

    public static Profile testProfileA() {
        return new Profile(
                null,
                "Hi I'm Michael",
                "1234567890",
                LocalDate.parse("2025-05-27"),
                25,
                null
        );
    }

    public static Profile testProfileB() {
        return new Profile(
                null,
                "Ari is an old man.",
                "8761239810",
                LocalDate.parse("2025-05-30"),
                30,
                null
        );
    }

    public static User testUserWithAddressAndProfileA() {
        User user = testUserA();

        Address addressA = testAddressA();
        user.addAddress(addressA);

        Address addressB = testAddressB();
        user.addAddress(addressB);

        Profile profile = testProfileA();
        user.addProfile(profile);

        return user;
    }

    public static User testUserWithAddressAndProfileB() {
        User user = testUserB();

        Address addressA = testAddressC();
        user.addAddress(addressA);

        Profile profile = testProfileB();
        user.addProfile(profile);

        return user;
    }

    public static CreateUserRequest testCreateUserRequestA() {
        return new CreateUserRequest(
                "Solomon Adi",
                "solomom.islands@gz.com",
                "passforpass123",
                null,
                null,
                null,
                null
        );
    }

    public static CreateUserRequest testCreateUserRequestWithAddressAndProfileB() {
        CreateUserRequest.CreateUserAddress createUserAddressA = testCreateUserAddressA();
        CreateUserRequest.CreateUserAddress createUserAddressB = testCreateUserAddressB();
        List<CreateUserRequest.CreateUserAddress> createUserAddresses = Arrays.asList(createUserAddressA, createUserAddressB);

        CreateUserRequest.CreateUserProfile createUserProfile = testCreateUserProfileA();

        Set<Integer> tagIds = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Long> productIds = new HashSet<>(Arrays.asList(1L, 2L, 3L));

        return new CreateUserRequest(
                "Solomon Adi",
                "solomom.islands@gz.com",
                "passforpass123",
                createUserAddresses,
                tagIds,
                createUserProfile,
                productIds
        );
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
        return new FullUpdateUserRequest(
                "Melamed Shai",
                "shai.melamed@gmail.com",
                "shai.starpass123",
                null,
                null,
                null,
                null
        );
    }

    public static FullUpdateUserRequest testFullUpdateUserRequestWithAddressAndProfileB() {
        FullUpdateUserRequest.FullUpdateUserAddress fullUpdateUserAddress = testFullUpdateUserAddressA();
        List<FullUpdateUserRequest.FullUpdateUserAddress> fullUpdateUserAddresses = new ArrayList<>();
        fullUpdateUserAddresses.add(fullUpdateUserAddress);

        FullUpdateUserRequest.FullUpdateUserProfile fullUpdateUserProfile = testFullUpdateUserProfileA();

        Set<Integer> tagIds = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Long> productIds = new HashSet<>(Arrays.asList(1L, 2L, 3L));

        return new FullUpdateUserRequest(
                "Melamed Shai",
                "shai.melamed@gmail.com",
                "shai.starpass123",
                fullUpdateUserAddresses,
                tagIds,
                fullUpdateUserProfile,
                productIds
        );
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

    public static FullUpdateUserRequest.FullUpdateUserProfile testFullUpdateUserProfileA() {
        return new FullUpdateUserRequest.FullUpdateUserProfile(
                "Jamie Olivier",
                "564-231-411",
                LocalDate.parse("2025-05-31"),
                55
        );
    }
}
