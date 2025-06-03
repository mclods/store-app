package com.mclods.store_app.validators;

import com.mclods.store_app.domain.dtos.user.PartialUpdateUserRequest;
import com.mclods.store_app.utils.TestDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PartialUpdateUserAddressUniqueIdsValidatorTests {
    @InjectMocks
    private PartialUpdateUserAddressUniqueIdsValidator partialUpdateUserAddressUniqueIdsValidator;

    @Test
    @DisplayName("Test isValid returns true if address ids are null")
    void testIsValidReturnsTrueIfAddressIdsAreNull() {
        PartialUpdateUserRequest.PartialUpdateUserAddress addressA = TestDataUtils.testPartialUpdateUserAddressA();
        PartialUpdateUserRequest.PartialUpdateUserAddress addressB = TestDataUtils.testPartialUpdateUserAddressB();

        addressA.setId(null);
        addressB.setId(null);

        boolean result = partialUpdateUserAddressUniqueIdsValidator.isValid(
                Arrays.asList(addressA, addressB),
                null
        );
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Test isValid returns true if address ids are unique")
    void testIsValidReturnsTrueIfAddressIdsAreUnique() {
        PartialUpdateUserRequest.PartialUpdateUserAddress addressA = TestDataUtils.testPartialUpdateUserAddressA();
        PartialUpdateUserRequest.PartialUpdateUserAddress addressB = TestDataUtils.testPartialUpdateUserAddressB();

        addressA.setId(1L);
        addressB.setId(2L);

        boolean result = partialUpdateUserAddressUniqueIdsValidator.isValid(
                Arrays.asList(addressA, addressB),
                null
        );
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Test isValid returns false if address ids are not unique")
    void testIsValidReturnsFalseIfAddressIdsAreNotUnique() {
        PartialUpdateUserRequest.PartialUpdateUserAddress addressA = TestDataUtils.testPartialUpdateUserAddressA();
        PartialUpdateUserRequest.PartialUpdateUserAddress addressB = TestDataUtils.testPartialUpdateUserAddressB();

        addressA.setId(1L);
        addressB.setId(1L);

        boolean result = partialUpdateUserAddressUniqueIdsValidator.isValid(
                Arrays.asList(addressA, addressB),
                null
        );
        assertThat(result).isFalse();
    }
}
