package com.mclods.store_app.domain.dtos.user;

import com.mclods.store_app.domain.dtos.common.AddressDto;
import com.mclods.store_app.domain.dtos.common.ProductDto;
import com.mclods.store_app.domain.dtos.common.ProfileDto;
import com.mclods.store_app.domain.dtos.common.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;

    private String name;

    private String email;

    private String password;

    private List<AddressDto> addresses = new ArrayList<>();

    private Set<TagDto> tags = new HashSet<>();

    private ProfileDto profile;

    private Set<ProductDto> wishlist = new HashSet<>();
}
