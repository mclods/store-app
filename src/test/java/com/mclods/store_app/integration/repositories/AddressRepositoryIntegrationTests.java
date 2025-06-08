package com.mclods.store_app.integration.repositories;

import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.repositories.AddressRepository;
import com.mclods.store_app.repositories.UserRepository;
import com.mclods.store_app.utils.TestDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Transactional
public class AddressRepositoryIntegrationTests {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Test address can be checked to exist by id and user id")
    void testAddressCanBeCheckedToExistByIdAndUserId() {
        User testUserA = TestDataUtils.testUserWithAddressAndProfileA(),
                testUserB = TestDataUtils.testUserWithAddressAndProfileB();

        userRepository.save(testUserA);
        userRepository.save(testUserB);

        Long testUserAId = testUserA.getId(),
                testUserAAddressAId = testUserA.getAddresses().get(0).getId(),
                testUserBAddressAId = testUserB.getAddresses().get(0).getId();

        boolean exists = addressRepository.existsByIdAndUserId(testUserAAddressAId, testUserAId);
        assertThat(exists).isTrue();

        exists = addressRepository.existsByIdAndUserId(testUserBAddressAId, testUserAId);
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Test all addresses can be retrieved by user id")
    void testAllAddressesCanBeRetrievedByUserId() {
        User testUserA = TestDataUtils.testUserWithAddressAndProfileA(),
                testUserB = TestDataUtils.testUserWithAddressAndProfileB();

        userRepository.save(testUserA);
        userRepository.save(testUserB);

        List<Address> testUserAAddresses = new ArrayList<>();
        addressRepository.findAllByUserId(testUserA.getId()).forEach(testUserAAddresses::add);

        // Assert Addresses
        assertThat(testUserAAddresses).hasSize(2);
        assertThat(testUserAAddresses)
                .extracting(Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                testUserA.getAddresses().get(0).getStreet(),
                                testUserA.getAddresses().get(0).getCity(),
                                testUserA.getAddresses().get(0).getZip(),
                                testUserA.getAddresses().get(0).getState()
                        ),
                        tuple(
                                testUserA.getAddresses().get(1).getStreet(),
                                testUserA.getAddresses().get(1).getCity(),
                                testUserA.getAddresses().get(1).getZip(),
                                testUserA.getAddresses().get(1).getState()
                        )
                );
    }
}
