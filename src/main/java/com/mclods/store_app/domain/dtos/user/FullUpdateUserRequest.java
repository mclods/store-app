package com.mclods.store_app.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mclods.store_app.validators.UniqueIds;
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
public class FullUpdateUserRequest {
    private Long id;

    @NotNull(message = "user name cannot be null")
    private String name;

    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "password cannot be null")
    private String password;

    @Valid
    @UniqueIds(message = "Address Ids must be unique")
    @Builder.Default
    private List<FullUpdateUserAddress> addresses = new ArrayList<>();

    @Valid
    private FullUpdateUserProfile profile;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FullUpdateUserAddress {
        private Long id;

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
    @Builder
    public static class FullUpdateUserProfile {
        private String bio;

        private String phoneNumber;

        private LocalDate dateOfBirth;

        private Integer loyaltyPoints;
    }
}
