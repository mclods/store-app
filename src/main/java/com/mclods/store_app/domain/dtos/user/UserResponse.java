package com.mclods.store_app.domain.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;

    private String name;

    private String email;

    private String password;

    private List<UserAddress> addresses = new ArrayList<>();

    private UserProfile profile;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserAddress {
        private Long id;

        private String street;

        private String city;

        private String zip;

        private String state;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProfile {
        private String bio;

        private String phoneNumber;

        private LocalDate dateOfBirth;

        private Integer loyaltyPoints;
    }
}
