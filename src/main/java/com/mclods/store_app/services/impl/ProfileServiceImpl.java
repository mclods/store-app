package com.mclods.store_app.services.impl;

import com.mclods.store_app.repositories.ProfileRepository;
import com.mclods.store_app.services.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public boolean exists(Long id) {
        return profileRepository.existsById(id);
    }
}
