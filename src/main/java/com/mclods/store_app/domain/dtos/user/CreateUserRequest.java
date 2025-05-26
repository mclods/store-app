package com.mclods.store_app.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
    @NotNull(message = "user name cannot be null")
    private String name;

    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "password cannot be null")
    private String password;

    @Valid
    private List<CreateUserAddress> addresses = new ArrayList<>();

    private Set<Integer> tagsIds = new HashSet<>();

    @Valid
    private CreateUserProfile profile;

    private Set<Long> productIds = new HashSet<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateUserAddress {
        @NotNull(message = "street cannot be null")
        private String street;

        @NotNull(message = "city cannot be null")
        private String city;

        @NotNull(message = "zip cannot be null")
        private String zip;

        @NotNull(message = "state cannot be null")
        private String state;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateUserProfile {
        private String bio;

        private String phoneNumber;

        private LocalDate dateOfBirth;

        private Integer loyaltyPoints;
    }
}
