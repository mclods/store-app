package com.mclods.store_app.services;

import com.mclods.store_app.domain.entities.User;

import java.util.Optional;

public interface UserService {
    User save(User user);
    User save(Long id, User user);
    Optional<User> findOne(Long id);
    boolean exists(Long id);
}
