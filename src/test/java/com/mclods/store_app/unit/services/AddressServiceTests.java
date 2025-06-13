package com.mclods.store_app.unit.services;

import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.repositories.AddressRepository;
import com.mclods.store_app.services.impl.AddressServiceImpl;
import com.mclods.store_app.utils.TestDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    @DisplayName("Test exists checks address with id exists")
    void testExistsChecksAddressWithIdExists() {
        when(addressRepository.existsById(anyLong())).thenReturn(true);

        boolean profileExists = addressService.exists(1L);
        verify(addressRepository).existsById(1L);
        assertThat(profileExists).isTrue();
    }

    @Test
    @DisplayName("Test exists with user id checks address with id and user id exists")
    void testExistsWithUserIdChecksAddressWithIdAndUserIdExists() {
        when(addressRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(true);

        boolean profileExists = addressService.existsWithUserId(1L, 2L);
        verify(addressRepository).existsByIdAndUserId(1L, 2L);
        assertThat(profileExists).isTrue();
    }

    @Test
    @DisplayName("Test find all by user id retrieves all addresses by user id")
    void testFindAllByUserIdRetrievesAllAddressesByUserId() {
        when(addressRepository.findAllByUserId(anyLong()))
                .thenReturn(List.of(TestDataUtils.testAddressA(), TestDataUtils.testAddressB()));

        List<Address> foundAddresses = addressService.findAllByUserId(1L);
        assertThat(foundAddresses)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAnyElementsOf(List.of(TestDataUtils.testAddressA(), TestDataUtils.testAddressB()));
    }
}
