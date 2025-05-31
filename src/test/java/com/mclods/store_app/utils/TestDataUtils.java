package com.mclods.store_app.utils;

import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;

import java.time.LocalDate;

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

    public static User testUserWithAddressAndProfileA() {
        User user = testUserA();

        Address addressA = new Address(
                null,
                "Ben Yehuda Alley",
                "Tel Aviv - Jaffa",
                "123-456",
                "Israel",
                null
        );
        Address addressB = new Address(
                null,
                "16 Yafo Street",
                "Jerusalem",
                "765-210",
                "Israel",
                null
        );

        user.addAddress(addressA);
        user.addAddress(addressB);

        Profile profile = new Profile(
                null,
                "Hi I'm Michael",
                "1234567890",
                LocalDate.parse("2025-05-27"),
                25,
                null
        );

        user.addProfile(profile);

        return user;
    }

    public static User testUserWithAddressAndProfileB() {
        User user = testUserB();

        Address addressA = new Address(
                null,
                "4 Hamatechet",
                "Hadera",
                "541-000",
                "Israel",
                null
        );

        user.addAddress(addressA);

        Profile profile = new Profile(
                null,
                "Ari is an old man.",
                "8761239810",
                LocalDate.parse("2025-05-30"),
                30,
                null
        );

        user.addProfile(profile);

        return user;
    }

    public static CreateUserRequest.CreateUserAddress testCreateUserAddressA() {
        return new CreateUserRequest.CreateUserAddress(
                "Ben Yehuda Alley",
                "Tel Aviv - Jaffa",
                "123-456",
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

    public static FullUpdateUserRequest.FullUpdateUserAddress testFullUpdateUserAddressA() {
        return new FullUpdateUserRequest.FullUpdateUserAddress(
                10L,
                "Ben Yehuda Alley",
                "Tel Aviv - Jaffa",
                "123-456",
                "Israel"
        );
    }

    public static FullUpdateUserRequest.FullUpdateUserProfile testFullUpdateUserProfileA() {
        return new FullUpdateUserRequest.FullUpdateUserProfile(
                "Hi I'm Camelot",
                "564-231-411",
                LocalDate.parse("2025-05-31"),
                55
        );
    }
}
