package com.mclods.store_app.unit.services;

import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.repositories.UserRepository;
import com.mclods.store_app.services.ProfileService;
import com.mclods.store_app.services.impl.UserServiceImpl;
import com.mclods.store_app.utils.TestDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileService profileService;

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
    @DisplayName("Test save with id saves user with same id")
    void testSaveWithIdSavesUserWithSameId() {
        User testUser = TestDataUtils.testUserWithAddressAndProfileA();
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(profileService.exists(anyLong())).thenReturn(true);

        userService.save(999L, testUser);
        verify(userRepository).save(argThat(user -> user.getId().equals(999L)
                && user.getProfile().getId().equals(999L)));
    }

    @Test
    @DisplayName("Test find one returns an optional user")
    void testFindOneReturnsAnOptionalUser() {
        User testUser = TestDataUtils.testUserA();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userService.findOne(1L);
        assertThat(foundUser).isPresent();
    }

    @Test
    @DisplayName("Test exists returns boolean value")
    void testExistsReturnsBooleanValue() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        boolean userExists = userService.exists(1L);
        assertThat(userExists).isFalse();
    }
}
