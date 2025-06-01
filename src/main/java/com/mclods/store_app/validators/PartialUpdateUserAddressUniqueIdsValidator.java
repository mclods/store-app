package com.mclods.store_app.validators;

import com.mclods.store_app.domain.dtos.user.PartialUpdateUserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;

public class PartialUpdateUserAddressUniqueIdsValidator implements ConstraintValidator<UniqueIds, List<PartialUpdateUserRequest.PartialUpdateUserAddress>> {
    @Override
    public boolean isValid(List<PartialUpdateUserRequest.PartialUpdateUserAddress> addresses, ConstraintValidatorContext constraintValidatorContext) {
        if(addresses == null) {
            return true;
        }

        HashSet<Long> addressIds = new HashSet<>();
        boolean valid = true;
        for(PartialUpdateUserRequest.PartialUpdateUserAddress address : addresses) {
            Long id = address.getId();

            if(id != null) {
                if(addressIds.contains(id)) {
                    valid = false;
                    break;
                } else {
                    addressIds.add(id);
                }
            }
        }

        return valid;
    }
}
