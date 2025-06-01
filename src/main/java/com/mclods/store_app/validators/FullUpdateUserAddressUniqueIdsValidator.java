package com.mclods.store_app.validators;

import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;

public class FullUpdateUserAddressUniqueIdsValidator implements ConstraintValidator<UniqueIds, List<FullUpdateUserRequest.FullUpdateUserAddress>> {
    @Override
    public boolean isValid(List<FullUpdateUserRequest.FullUpdateUserAddress> addresses, ConstraintValidatorContext constraintValidatorContext) {
        if(addresses == null) {
            return true;
        }

        HashSet<Long> addressIds = new HashSet<>();
        boolean valid = true;
        for(FullUpdateUserRequest.FullUpdateUserAddress address : addresses) {
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
