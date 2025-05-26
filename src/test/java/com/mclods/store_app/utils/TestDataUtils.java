package com.mclods.store_app.utils;

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
}
