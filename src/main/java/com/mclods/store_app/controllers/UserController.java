package com.mclods.store_app.controllers;

import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
import com.mclods.store_app.domain.dtos.user.UserResponse;
import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.mappers.UserMapper;
import com.mclods.store_app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        User userToSave = userMapper.mapCreateUserRequestToUser(createUserRequest);
        User savedUser = userService.save(userToSave);
        return new ResponseEntity<>(userMapper.mapUserToUserResponse(savedUser), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserResponse> findOneUser(@PathVariable("id") Long id) {
        Optional<User> foundUser = userService.findOne(id);
        return foundUser.map((user) ->
                new ResponseEntity<>(userMapper.mapUserToUserResponse(user), HttpStatus.OK))
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<UserResponse> fullUpdateUser(
            @PathVariable("id") Long id,
            @RequestBody @Valid FullUpdateUserRequest fullUserUpdateRequest
    ) {
        if(!userService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User userToUpdate = userMapper.mapFullUpdateUserRequestToUser(fullUserUpdateRequest);
        User updatedUser = userService.fullUpdateUser(id, userToUpdate);
        return new ResponseEntity<>(userMapper.mapUserToUserResponse(updatedUser), HttpStatus.OK);
    }
}
