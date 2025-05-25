package com.mclods.store_app.mappers;

import com.mclods.store_app.domain.dtos.common.AddressDto;
import com.mclods.store_app.domain.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AddressMapper {
    public abstract Address mapAddressDtoToAddress(AddressDto addressDto);
}
