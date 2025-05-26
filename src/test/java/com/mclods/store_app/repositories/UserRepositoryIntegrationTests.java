package com.mclods.store_app.repositories;

import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.utils.TestDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Optional;

@SpringBootTest
@Transactional
public class UserRepositoryIntegrationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Test user can be created and recalled")
    void testUserCanBeCreatedAndRecalled() {
        User testUser = TestDataUtils.testUserA();

        userRepository.save(testUser);
        Optional<User> foundUser = userRepository.findById(testUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get())
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly(testUser.getName(), testUser.getEmail(), testUser.getPassword());
    }

    @Test
    @DisplayName("Test user with address and profile can be created and recalled")
    void testUserWithAddressAndProfileCanBeCreatedAndRecalled() {
        User testUser = TestDataUtils.testUserWithAddressAndProfileA();
        Address testUserAddressA = testUser.getAddresses().get(0),
                testUserAddressB = testUser.getAddresses().get(1);
        Profile testUserProfile = testUser.getProfile();

        userRepository.save(testUser);
        Optional<User> foundUser = userRepository.findById(testUser.getId());

        assertThat(foundUser).isPresent();

        // Assert User
        assertThat(foundUser.get())
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly(testUser.getName(), testUser.getEmail(), testUser.getPassword());

        // Assert Addresses
        assertThat(foundUser.get().getAddresses())
                .extracting(Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                testUserAddressA.getStreet(),
                                testUserAddressA.getCity(),
                                testUserAddressA.getZip(),
                                testUserAddressA.getState()
                        ),
                        tuple(
                                testUserAddressB.getStreet(),
                                testUserAddressB.getCity(),
                                testUserAddressB.getZip(),
                                testUserAddressB.getState()
                        )
                );

        // Assert Profile
        assertThat(foundUser.get().getProfile())
                .extracting(Profile::getBio, Profile::getPhoneNumber,
                        Profile::getDateOfBirth, Profile::getLoyaltyPoints)
                .containsExactly(testUserProfile.getBio(), testUserProfile.getPhoneNumber(),
                        testUserProfile.getDateOfBirth(), testUserProfile.getLoyaltyPoints());
    }
}
