package com.mclods.store_app.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long id;

    private String bio;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private Integer loyaltyPoints;

    private UserDto user;
}
