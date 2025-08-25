package com.mclods.store_app.services;

import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    User fullUpdateUser(Long id, User user) throws UserNotFoundException;
    User partialUpdateUser(Long id, User user) throws UserNotFoundException;
    Optional<User> findOne(Long id);
    List<User> findAll();
    boolean exists(Long id);
    void delete(Long id);
}
