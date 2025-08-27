package com.mclods.store_app.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mclods.store_app.validators.UniqueIds;
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
public class PartialUpdateUserRequest {
    private Long id;

    private String name;

    private String email;

    private String password;

    @UniqueIds(message = "Address Ids must be unique")
    @Builder.Default
    private List<PartialUpdateUserAddress> addresses = new ArrayList<>();

    private PartialUpdateUserProfile profile;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PartialUpdateUserAddress {
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
    @Builder
    public static class PartialUpdateUserProfile {
        private String bio;

        private String phoneNumber;

        private LocalDate dateOfBirth;

        private Integer loyaltyPoints;
    }
}
