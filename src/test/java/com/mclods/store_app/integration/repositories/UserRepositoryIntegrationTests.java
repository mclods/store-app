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
        Optional<User> savedUser = userRepository.findUser(testUser.getId());

        assertThat(savedUser).isPresent();
        assertThat(savedUser.get())
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly(testUser.getName(), testUser.getEmail(), testUser.getPassword());
    }

    @Test
    @DisplayName("Test user with address and profile can be created and recalled")
    void testUserWithAddressAndProfileCanBeCreatedAndRecalled() {
        User testUser = TestDataUtils.testUserWithAddressAndProfileA();

        userRepository.save(testUser);
        Optional<User> savedUser = userRepository.findUser(testUser.getId());

        assertThat(savedUser).isPresent();

        // Assert User
        assertThat(savedUser.get())
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly(testUser.getName(), testUser.getEmail(), testUser.getPassword());

        // Assert Addresses
        assertThat(savedUser.get().getAddresses()).hasSize(2);
        assertThat(savedUser.get().getAddresses())
                .extracting(Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                testUser.getAddresses().get(0).getStreet(),
                                testUser.getAddresses().get(0).getCity(),
                                testUser.getAddresses().get(0).getZip(),
                                testUser.getAddresses().get(0).getState()
                        ),
                        tuple(
                                testUser.getAddresses().get(1).getStreet(),
                                testUser.getAddresses().get(1).getCity(),
                                testUser.getAddresses().get(1).getZip(),
                                testUser.getAddresses().get(1).getState()
                        )
                );

        // Assert Profile
        assertThat(savedUser.get().getProfile())
                .extracting(Profile::getBio, Profile::getPhoneNumber,
                        Profile::getDateOfBirth, Profile::getLoyaltyPoints)
                .containsExactly(
                        testUser.getProfile().getBio(),
                        testUser.getProfile().getPhoneNumber(),
                        testUser.getProfile().getDateOfBirth(),
                        testUser.getProfile().getLoyaltyPoints()
                );
    }

    @Test
    @DisplayName("Test user can be fully updated")
    void testUserCanBeUpdated() {
        User testUser = TestDataUtils.testUserWithAddressAndProfileA();
        userRepository.save(testUser);

        User savedUser = userRepository.findUser(testUser.getId()).orElseThrow();

        User userToUpdate = TestDataUtils.testUserB();
        userToUpdate.setId(savedUser.getId());

        Address userAddressToUpdate = Address.builder()
                .id(savedUser.getAddresses().get(0).getId())
                .street("Azrieli Center 34th Floor")
                .city("Tel Aviv")
                .zip("461-211")
                .state("Israel")
                .build();
        userToUpdate.addAddress(userAddressToUpdate);
        userRepository.save(userToUpdate);

        Optional<User> updatedUser = userRepository.findUser(savedUser.getId());

        assertThat(updatedUser).isPresent();

        // Assert User
        assertThat(updatedUser.get())
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly(testUser.getName(), testUser.getEmail(), testUser.getPassword());

        // Assert Addresses
        assertThat(updatedUser.get().getAddresses()).hasSize(1);
        assertThat(updatedUser.get().getAddresses())
                .extracting(Address::getId, Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                savedUser.getAddresses().get(0).getId(),
                                "Azrieli Center 34th Floor",
                                "Tel Aviv",
                                "461-211",
                                "Israel"
                        )
                );

        // Assert Profile
        assertThat(updatedUser.get().getProfile()).isNull();
    }

    @Test
    @DisplayName("Test user can be partially updated")
    void testUserCanBePartiallyUpdated() {
        User testUser = TestDataUtils.testUserWithAddressAndProfileA();
        userRepository.save(testUser);

        testUser.setName("Chemical Michael");

        Address testAddressA = testUser.getAddresses().get(0);
        testAddressA.setStreet("Coleman St.");

        Profile testProfile = testUser.getProfile();
        testProfile.setBio("It's my new updated bio");
        testProfile.setPhoneNumber(null);

        userRepository.save(testUser);

        Optional<User> savedUser = userRepository.findUser(testUser.getId());

        assertThat(savedUser).isPresent();

        // Assert User
        assertThat(savedUser.get())
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly("Chemical Michael", testUser.getEmail(), testUser.getPassword());

        // Assert Addresses
        assertThat(savedUser.get().getAddresses()).hasSize(2);
        assertThat(savedUser.get().getAddresses())
                .extracting(Address::getStreet, Address::getCity,
                        Address::getZip, Address::getState)
                .containsExactly(
                        tuple(
                                "Coleman St.",
                                testUser.getAddresses().get(0).getCity(),
                                testUser.getAddresses().get(0).getZip(),
                                testUser.getAddresses().get(0).getState()
                        ),
                        tuple(
                                testUser.getAddresses().get(1).getStreet(),
                                testUser.getAddresses().get(1).getCity(),
                                testUser.getAddresses().get(1).getZip(),
                                testUser.getAddresses().get(1).getState()
                        )
                );

        // Assert Profile
        assertThat(savedUser.get().getProfile())
                .extracting(Profile::getBio, Profile::getPhoneNumber, Profile::getDateOfBirth, Profile::getLoyaltyPoints)
                .containsExactly(
                        "It's my new updated bio",
                        null,
                        testUser.getProfile().getDateOfBirth(),
                        testUser.getProfile().getLoyaltyPoints()
                );
    }

    @Test
    @DisplayName("Test user can be deleted")
    void testUserCanBeDeleted() {
        User testUser = TestDataUtils.testUserA();
        userRepository.save(testUser);

        boolean exists = userRepository.existsById(testUser.getId());
        assertThat(exists).isTrue();

        userRepository.deleteById(testUser.getId());

        exists = userRepository.existsById(testUser.getId());
        assertThat(exists).isFalse();
    }
}
