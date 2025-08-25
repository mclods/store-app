package com.mclods.store_app.unit.services;

import com.mclods.store_app.domain.entities.Address;
import com.mclods.store_app.domain.entities.Profile;
import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.exceptions.AddressHasMissingFieldsException;
import com.mclods.store_app.exceptions.UserNotFoundException;
import com.mclods.store_app.repositories.UserRepository;
import com.mclods.store_app.services.impl.UserServiceImpl;
import com.mclods.store_app.utils.TestDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Test save returns the saved user")
    void testSaveReturnsTheSavedUser() {
        User testUser = TestDataUtils.testUserA();
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User savedUser = userService.save(testUser);
        assertThat(savedUser)
                .extracting(User::getName, User::getEmail, User::getPassword)
                .containsExactly(testUser.getName(), testUser.getEmail(), testUser.getPassword());
    }

    @Test
    @DisplayName("Test full update user saves user with same id")
    void testFullUpdateUserSavesUserWithSameId() throws UserNotFoundException {
        User testUser = TestDataUtils.testUserWithAddressAndProfileA();
        User existingUser = TestDataUtils.testExistingUserWithAddressAndProfileA();

        when(userRepository.findUser(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.fullUpdateUser(1L, testUser);
        verify(userRepository).save(argThat(user -> user.getId().equals(1L)
                && user.getProfile().getId().equals(1L)));
    }

    @Test
    @DisplayName("Test full update user cleans invalid address ids before saving")
    void testFullUpdateUserCleansInvalidAddressIdsBeforeSaving() throws UserNotFoundException {
        User testUser = TestDataUtils.testUserA();
        Address testAddress = TestDataUtils.testAddressA();

        testAddress.setId(999L);
        testUser.addAddress(testAddress);

        User existingUser = TestDataUtils.testExistingUserWithAddressAndProfileA();

        when(userRepository.findUser(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.fullUpdateUser(1L, testUser);
        verify(userRepository).save(argThat(user -> user.getAddresses().get(0).getId() == null));
    }

    @Test
    @DisplayName("Test full update user cleans profile id before saving")
    void testFullUpdateUserCleansProfileIdBeforeSaving() throws UserNotFoundException {
        User testUser = TestDataUtils.testUserA();
        Profile testProfile = TestDataUtils.testProfileA();

        testProfile.setId(999L);
        testUser.addProfile(testProfile);

        User existingUser = TestDataUtils.testExistingUserWithAddressAndProfileA();

        when(userRepository.findUser(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.fullUpdateUser(1L, testUser);
        verify(userRepository).save(argThat(user -> user.getProfile().getId()
                .equals(existingUser.getProfile().getId())));
    }

    @Test
    @DisplayName("Test full update user throws UserNotFoundException if user does not exist")
    void testFullUpdateUserThrowsUserNotFoundExceptionIfUserDoesNotExist() {
        User testUser = TestDataUtils.testUserA();
        when(userRepository.findUser(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> userService.fullUpdateUser(1L, testUser));
    }

    @Test
    @DisplayName("Test partial update user partially updates the user")
    void testPartialUpdateUserPartiallyUpdatesTheUser() throws UserNotFoundException {
        User existingUser = TestDataUtils.testUserA();
        existingUser.setId(1L);

        when(userRepository.findUser(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User partiallyUpdatedUser = TestDataUtils.partiallyUpdatedUserA();

        userService.partialUpdateUser(1L, partiallyUpdatedUser);
        verify(userRepository).save(argThat(userToSave ->
            userToSave.getId().equals(1L) &&
            userToSave.getName().equals(partiallyUpdatedUser.getName()) &&
            userToSave.getEmail().equals(existingUser.getEmail()) &&
            userToSave.getPassword().equals(existingUser.getPassword()) &&
            userToSave.getAddresses().isEmpty() &&
            userToSave.getProfile() == null &&
            userToSave.getTags().isEmpty() &&
            userToSave.getWishlist().isEmpty()
        ));
    }

    @Test
    @DisplayName("Test partial update user creates new address if address id is not passed")
    void testPartialUpdateUserCreatesNewAddressIfAddressIdIsNotPassed() throws UserNotFoundException {
        User existingUser = TestDataUtils.testUserA();
        existingUser.setId(1L);

        when(userRepository.findUser(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User partiallyUpdatedUser = TestDataUtils.testPartiallyUpdatedUserWithAddressAndProfileA();
        Address addressToUpdateA = partiallyUpdatedUser.getAddresses().get(0),
                addressToUpdateB = partiallyUpdatedUser.getAddresses().get(1);

        userService.partialUpdateUser(1L, partiallyUpdatedUser);
        verify(userRepository).save(argThat(userToSave ->
                userToSave.getId().equals(1L) &&
                userToSave.getAddresses().get(0).getId() == null &&
                userToSave.getAddresses().get(0).getStreet().equals(addressToUpdateA.getStreet()) &&
                userToSave.getAddresses().get(0).getCity().equals(addressToUpdateA.getCity()) &&
                userToSave.getAddresses().get(0).getZip().equals(addressToUpdateA.getZip()) &&
                userToSave.getAddresses().get(0).getState().equals(addressToUpdateA.getState()) &&

                userToSave.getAddresses().get(1).getId() == null &&
                userToSave.getAddresses().get(1).getStreet().equals(addressToUpdateB.getStreet()) &&
                userToSave.getAddresses().get(1).getCity().equals(addressToUpdateB.getCity()) &&
                userToSave.getAddresses().get(1).getZip().equals(addressToUpdateB.getZip()) &&
                userToSave.getAddresses().get(1).getState().equals(addressToUpdateB.getState())

        ));
    }

    @Test
    @DisplayName("Test partial update user throws AddressHasMissingFieldsException if address to be created has missing fields")
    void testPartialUpdateUserThrowsAddressHasMissingFieldsExceptionIfAddressToBeCreatedHasMissingFields() {
        User existingUser = TestDataUtils.testUserA();
        existingUser.setId(1L);

        when(userRepository.findUser(anyLong())).thenReturn(Optional.of(existingUser));

        User partiallyUpdatedUser = TestDataUtils.partiallyUpdatedUserA();
        Address partiallyUpdatedUserAddress = Address.builder()
                .street("Gerome Street")
                .state("Alaska State")
                .build();

        partiallyUpdatedUser.addAddress(partiallyUpdatedUserAddress);

        assertThatThrownBy(() -> userService.partialUpdateUser(1L, partiallyUpdatedUser))
                .isInstanceOf(RuntimeException.class)
                .hasCauseExactlyInstanceOf(AddressHasMissingFieldsException.class);
    }

    @Test
    @DisplayName("Test partial update user creates new address if address id is not valid")
    void testPartialUpdateUserCreatesNewAddressIfAddressIdIsNotValid() throws UserNotFoundException {
        User existingUser = TestDataUtils.testUserWithAddressAndProfileB();
        existingUser.setId(1L);
        existingUser.getAddresses().get(0).setId(1L);
        existingUser.getProfile().setId(1L);

        when(userRepository.findUser(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User partiallyUpdatedUser = TestDataUtils.testPartiallyUpdatedUserWithAddressAndProfileB();
        Address addressToUpdateA = partiallyUpdatedUser.getAddresses().get(0);
        addressToUpdateA.setId(2L);

        userService.partialUpdateUser(1L, partiallyUpdatedUser);
        verify(userRepository).save(argThat(userToSave ->
                userToSave.getId().equals(1L) &&
                userToSave.getAddresses().get(0).getId().equals(1L) &&
                userToSave.getAddresses().get(0).getStreet().equals(existingUser.getAddresses().get(0).getStreet()) &&
                userToSave.getAddresses().get(0).getCity().equals(existingUser.getAddresses().get(0).getCity()) &&
                userToSave.getAddresses().get(0).getZip().equals(existingUser.getAddresses().get(0).getZip()) &&
                userToSave.getAddresses().get(0).getState().equals(existingUser.getAddresses().get(0).getState()) &&

                addressToUpdateA.getId() == null &&
                userToSave.getAddresses().get(1).getStreet().equals(addressToUpdateA.getStreet()) &&
                userToSave.getAddresses().get(1).getCity().equals(addressToUpdateA.getCity()) &&
                userToSave.getAddresses().get(1).getZip().equals(addressToUpdateA.getZip()) &&
                userToSave.getAddresses().get(1).getState().equals(addressToUpdateA.getState())
        ));
    }

    @Test
    @DisplayName("Test partial update user partially updates address if address id belongs to an existing address")
    void testPartialUpdateUserPartiallyUpdatesAddressIfAddressIdBelongsToAnExistingAddress() throws UserNotFoundException {
        User existingUser = TestDataUtils.testUserWithAddressAndProfileB();
        existingUser.setId(1L);
        existingUser.getAddresses().get(0).setId(1L);
        existingUser.getProfile().setId(1L);

        when(userRepository.findUser(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User partiallyUpdatedUser = TestDataUtils.partiallyUpdatedUserA();
        Address partiallyUpdatedUserAddress = Address.builder()
                .id(1L)
                .street("Peterson St.")
                .state("Sweden")
                .build();

        partiallyUpdatedUser.addAddress(partiallyUpdatedUserAddress);

        userService.partialUpdateUser(1L, partiallyUpdatedUser);
        verify(userRepository).save(argThat(userToSave ->
                userToSave.getId().equals(1L) &&
                userToSave.getAddresses().get(0).getId().equals(1L) &&
                userToSave.getAddresses().get(0).getStreet().equals("Peterson St.") &&
                userToSave.getAddresses().get(0).getCity().equals(existingUser.getAddresses().get(0).getCity()) &&
                userToSave.getAddresses().get(0).getZip().equals(existingUser.getAddresses().get(0).getZip()) &&
                userToSave.getAddresses().get(0).getState().equals("Sweden")
        ));
    }

    @Test
    @DisplayName("Test partial update user creates a new profile")
    void testPartialUpdateUserCreatesANewProfile() throws UserNotFoundException {
        User existingUser = TestDataUtils.testUserA();
        existingUser.setId(1L);

        when(userRepository.findUser(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User partiallyUpdatedUser = TestDataUtils.partiallyUpdatedUserA();
        Profile partiallyUpdatedUserProfile = Profile.builder()
                .id(3L)
                .bio("Best Bio Ever")
                .dateOfBirth(LocalDate.parse("2020-05-12"))
                .build();

        partiallyUpdatedUser.addProfile(partiallyUpdatedUserProfile);

        userService.partialUpdateUser(1L, partiallyUpdatedUser);
        verify(userRepository).save(argThat(userToSave ->
                userToSave.getId().equals(1L) &&
                userToSave.getProfile().getId() == null &&
                userToSave.getProfile().getBio().equals("Best Bio Ever") &&
                userToSave.getProfile().getPhoneNumber() == null &&
                userToSave.getProfile().getDateOfBirth().isEqual(LocalDate.parse("2020-05-12")) &&
                userToSave.getProfile().getLoyaltyPoints() == null
        ));
    }

    @Test
    @DisplayName("Test partial update user partially updates an existing profile")
    void testPartialUpdateUserPartiallyUpdatesAnExistingProfile() throws UserNotFoundException {
        User existingUser = TestDataUtils.testUserWithAddressAndProfileA();
        existingUser.setId(1L);
        existingUser.getProfile().setId(1L);

        when(userRepository.findUser(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User partiallyUpdatedUser = TestDataUtils.partiallyUpdatedUserA();
        Profile partiallyUpdatedUserProfile = Profile.builder()
                .id(1L)
                .bio("Best Bio Ever")
                .dateOfBirth(LocalDate.parse("2020-05-12"))
                .build();

        partiallyUpdatedUser.addProfile(partiallyUpdatedUserProfile);

        userService.partialUpdateUser(1L, partiallyUpdatedUser);
        verify(userRepository).save(argThat(userToSave ->
                userToSave.getId().equals(1L) &&
                userToSave.getProfile().getId().equals(1L) &&
                userToSave.getProfile().getBio().equals("Best Bio Ever") &&
                userToSave.getProfile().getPhoneNumber().equals(existingUser.getProfile().getPhoneNumber()) &&
                userToSave.getProfile().getDateOfBirth().isEqual(LocalDate.parse("2020-05-12")) &&
                userToSave.getProfile().getLoyaltyPoints().equals(existingUser.getProfile().getLoyaltyPoints())
        ));
    }

    @Test
    @DisplayName("Test find one returns an optional user")
    void testFindOneReturnsAnOptionalUser() {
        User testUser = TestDataUtils.testUserA();
        when(userRepository.findUser(anyLong())).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userService.findOne(1L);
        assertThat(foundUser).isPresent();
    }

    @Test
    @DisplayName("Test find all returns all users")
    void testFindAllReturnsAllUsers() {
        when(userRepository.findAllUsers()).thenReturn(List.of(TestDataUtils.testUserA(), TestDataUtils.testUserB()));

        List<User> foundUsers = userService.findAll();
        assertThat(foundUsers)
                .usingRecursiveFieldByFieldElementComparator()
                .containsAnyElementsOf(List.of(TestDataUtils.testUserA(), TestDataUtils.testUserB()));
    }

    @Test
    @DisplayName("Test find all returns empty list")
    void testFindAllReturnsEmptyList() {
        when(userRepository.findAllUsers()).thenReturn(List.of());

        List<User> foundUsers = userService.findAll();
        assertThat(foundUsers).isEmpty();
    }

    @Test
    @DisplayName("Test exists checks user with id exists")
    void testExistsChecksUserWithIdExists() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        boolean userExists = userService.exists(1L);
        verify(userRepository).existsById(1L);
        assertThat(userExists).isFalse();
    }

    @Test
    @DisplayName("Test delete user deletes an user")
    void testDeleteUserDeletesAnUser() {
        userService.delete(9999L);
        verify(userRepository).deleteById(anyLong());
    }
}
