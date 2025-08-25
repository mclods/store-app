package com.mclods.store_app.unit.validators;

import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
import com.mclods.store_app.utils.TestDataUtils;
import com.mclods.store_app.validators.FullUpdateUserAddressUniqueIdsValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FullUpdateUserAddressUniqueIdsValidatorTests {
    @InjectMocks
    private FullUpdateUserAddressUniqueIdsValidator fullUpdateUserAddressUniqueIdsValidator;

    @Test
    @DisplayName("Test isValid returns true if address ids are null")
    void testIsValidReturnsTrueIfAddressIdsAreNull() {
        FullUpdateUserRequest.FullUpdateUserAddress addressA = TestDataUtils.testFullUpdateUserAddressA();
        FullUpdateUserRequest.FullUpdateUserAddress addressB = TestDataUtils.testFullUpdateUserAddressB();

        addressA.setId(null);
        addressB.setId(null);

        boolean result = fullUpdateUserAddressUniqueIdsValidator.isValid(
                Arrays.asList(addressA, addressB), null);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Test isValid returns true if address ids are unique")
    void testIsValidReturnsTrueIfAddressIdsAreUnique() {
        FullUpdateUserRequest.FullUpdateUserAddress addressA = TestDataUtils.testFullUpdateUserAddressA();
        FullUpdateUserRequest.FullUpdateUserAddress addressB = TestDataUtils.testFullUpdateUserAddressB();

        addressA.setId(1L);
        addressB.setId(2L);

        boolean result = fullUpdateUserAddressUniqueIdsValidator.isValid(
                Arrays.asList(addressA, addressB), null);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Test isValid returns false if address ids are not unique")
    void testIsValidReturnsFalseIfAddressIdsAreNotUnique() {
        FullUpdateUserRequest.FullUpdateUserAddress addressA = TestDataUtils.testFullUpdateUserAddressA();
        FullUpdateUserRequest.FullUpdateUserAddress addressB = TestDataUtils.testFullUpdateUserAddressB();

        addressA.setId(1L);
        addressB.setId(1L);

        boolean result = fullUpdateUserAddressUniqueIdsValidator.isValid(
                Arrays.asList(addressA, addressB), null);

        assertThat(result).isFalse();
    }
}
