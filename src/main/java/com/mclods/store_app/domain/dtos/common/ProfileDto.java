package com.mclods.store_app.domain.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String bio;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private Integer loyaltyPoints;
}
