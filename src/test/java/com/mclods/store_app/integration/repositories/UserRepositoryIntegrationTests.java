package com.mclods.store_app.integration.repositories;

import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.repositories.UserRepository;
import com.mclods.store_app.utils.TestDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.LocalDate;
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
                .containsExactly("Michael Akrawi", "michael.akrawi@email.com", "michael123");
    }

    @Test
    @DisplayName("Test user with address and profile can be created and recalled")
    void testUserWithAddressAndProfileCanBeCreatedAndRecalled() {
        User testUser = TestDataUtils.testUserWithAddressAndProfileA();

        userRepository.save(testUser);
        Optional<User> foundUser = userRepository.findById(testUser.getId());

        assertThat(foundUser).isPresent();

        // Assert User
        assertThat(foundUser.get())
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly("Michael Akrawi", "michael.akrawi@email.com", "michael123");

        // Assert Addresses
        assertThat(foundUser.get().getAddresses()).hasSize(2);
        assertThat(foundUser.get().getAddresses())
                .extracting(Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                "Ben Yehuda Alley",
                                "Tel Aviv - Jaffa",
                                "123-456",
                                "Israel"
                        ),
                        tuple(
                                "16 Yafo Street",
                                "Jerusalem",
                                "765-210",
                                "Israel"
                        )
                );

        // Assert Profile
        assertThat(foundUser.get().getProfile())
                .extracting(Profile::getBio, Profile::getPhoneNumber,
                        Profile::getDateOfBirth, Profile::getLoyaltyPoints)
                .containsExactly(
                        "Hi I'm Michael",
                        "1234567890",
                        LocalDate.parse("2025-05-27"),
                        25
                );
    }

    @Test
    @DisplayName("Test user can be updated")
    void testUserCanBeUpdated() {
        User testUser = TestDataUtils.testUserWithAddressAndProfileA();
        userRepository.save(testUser);

        Address updatedAddress = new Address(
                testUser.getAddresses().get(0).getId(),
                "Azrieli Center 34th Floor",
                "Tel Aviv",
                "461-211",
                "Israel",
                null
        );

        User updatedUser = TestDataUtils.testUserB();
        updatedUser.setId(testUser.getId());
        updatedUser.addAddress(updatedAddress);

        userRepository.save(updatedUser);

        Optional<User> foundUser = userRepository.findById(updatedUser.getId());

        assertThat(foundUser).isPresent();

        // Assert User
        assertThat(foundUser.get())
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly("Ari Schwartz", "ari.schwartz@mailman.com", "49ari");

        // Assert Addresses
        assertThat(foundUser.get().getAddresses()).hasSize(1);
        assertThat(foundUser.get().getAddresses())
                .extracting(Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                "Azrieli Center 34th Floor",
                                "Tel Aviv",
                                "461-211",
                                "Israel"
                        )
                );

        // Assert Profile
        assertThat(foundUser.get().getProfile()).isNull();
    }
}
