package com.mclods.store_app.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String name;

    private String email;

    private String password;

    private List<AddressDto> addresses = new ArrayList<>();

    private Set<TagDto> tags = new HashSet<>();

    private ProfileDto profile;

    private Set<ProductDto> wishlist = new HashSet<>();
}
