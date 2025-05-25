package com.mclods.store_app.services.impl;

import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.repositories.UserRepository;
import com.mclods.store_app.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User save(Long id, User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> findOne(Long id) {
        return userRepository.findById(id);
    }
}
