package com.mclods.store_app.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mclods.store_app.validators.UniqueIds;
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
public class PartialUpdateUserRequest {
    private String name;

    private String email;

    private String password;

    @UniqueIds
    private List<PartialUpdateUserAddress> addresses = new ArrayList<>();

    private Set<Integer> tagsIds = new HashSet<>();

    private PartialUpdateUserProfile profile;

    private Set<Long> productIds = new HashSet<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
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
    public static class PartialUpdateUserProfile {
        private String bio;

        private String phoneNumber;

        private LocalDate dateOfBirth;

        private Integer loyaltyPoints;
    }
}
