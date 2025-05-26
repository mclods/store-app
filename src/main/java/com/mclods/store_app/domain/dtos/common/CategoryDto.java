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
public class CategoryDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Short id;

    @NotNull(message = "category name cannot be null")
    private String name;
}
