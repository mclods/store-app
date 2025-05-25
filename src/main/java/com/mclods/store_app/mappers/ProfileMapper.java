package com.mclods.store_app.mappers;

import com.mclods.store_app.domain.dtos.common.ProfileDto;
import com.mclods.store_app.domain.entities.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ProfileMapper {
    public abstract Profile mapProfileDtoToProfile(ProfileDto profileDto);
}
