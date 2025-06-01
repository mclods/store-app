package com.mclods.store_app.services;

import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.exceptions.AddressHasMissingFieldsException;
import com.mclods.store_app.exceptions.UserNotFoundException;

import java.util.Optional;

public interface UserService {
    User save(User user);
    User fullUpdateUser(Long id, User user);
    User partialUpdateUser(Long id, User user) throws UserNotFoundException, AddressHasMissingFieldsException;
    Optional<User> findOne(Long id);
    boolean exists(Long id);
}
