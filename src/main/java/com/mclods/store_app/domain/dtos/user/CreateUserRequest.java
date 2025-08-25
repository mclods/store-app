package com.mclods.store_app.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
    @NotNull(message = "user name cannot be null")
    private String name;

    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "password cannot be null")
    private String password;

    @Valid
    @Builder.Default
    private List<CreateUserAddress> addresses = new ArrayList<>();

    @Valid
    private CreateUserProfile profile;

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
