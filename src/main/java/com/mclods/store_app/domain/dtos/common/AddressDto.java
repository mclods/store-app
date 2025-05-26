package com.mclods.store_app.domain.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
