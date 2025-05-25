package com.mclods.store_app.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mclods.store_app.domain.dtos.common.AddressDto;
import com.mclods.store_app.domain.dtos.common.ProfileDto;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
    private String name;

    private String email;

    private String password;

    private List<AddressDto> addresses = new ArrayList<>();

    private Set<Integer> tagsIds = new HashSet<>();

    private ProfileDto profile;

    private Set<Long> productIds = new HashSet<>();
}
