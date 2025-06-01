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

        user.addAddress(testAddressA());
        user.addAddress(testAddressB());

        user.addProfile(testProfileA());

        return user;
    }

    public static User testUserWithAddressAndProfileB() {
        User user = testUserB();

        user.addAddress(testAddressC());

        user.addProfile(testProfileB());

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

    public static CreateUserRequest testCreateUserRequestWithAddressAndProfileA() {
        Set<Integer> tagIds = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Long> productIds = new HashSet<>(Arrays.asList(1L, 2L, 3L));

        return new CreateUserRequest(
                "Solomon Adi",
                "solomom.islands@gz.com",
                "passforpass123",
                Arrays.asList(testCreateUserAddressA(), testCreateUserAddressB()),
                tagIds,
                testCreateUserProfileA(),
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

    public static FullUpdateUserRequest testFullUpdateUserRequestWithAddressAndProfileA() {
        Set<Integer> tagIds = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Long> productIds = new HashSet<>(Arrays.asList(1L, 2L, 3L));

        return new FullUpdateUserRequest(
                "Melamed Shai",
                "shai.melamed@gmail.com",
                "shai.starpass123",
                List.of(testFullUpdateUserAddressA()),
                tagIds,
                testFullUpdateUserProfileA(),
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
        return new PartialUpdateUserRequest(
                "Crazy Toon",
                null,
                null,
                null,
                null,
                null,
                null
        );
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
