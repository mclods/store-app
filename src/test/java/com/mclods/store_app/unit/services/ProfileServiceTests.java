package com.mclods.store_app.unit.services;

import com.mclods.store_app.repositories.ProfileRepository;
import com.mclods.store_app.services.impl.ProfileServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTests {
    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    ProfileServiceImpl profileService;

    @Test
    @DisplayName("Test exists checks profile with id exists")
    void testExistsChecksProfileWithIdExists() {
        when(profileRepository.existsById(anyLong())).thenReturn(true);

        boolean profileExists = profileService.exists(1L);
        verify(profileRepository).existsById(1L);
        assertThat(profileExists).isTrue();
    }
}
