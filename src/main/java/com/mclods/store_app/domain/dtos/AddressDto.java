package com.mclods.store_app.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private Long id;

    private String street;

    private String city;

    private String zip;

    private String state;

    private UserDto user;
}
