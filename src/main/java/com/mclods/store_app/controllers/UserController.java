package com.mclods.store_app.controllers;

import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
import com.mclods.store_app.domain.dtos.user.PartialUpdateUserRequest;
import com.mclods.store_app.domain.dtos.user.UserResponse;
import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.exceptions.AddressHasMissingFieldsException;
import com.mclods.store_app.exceptions.UserNotFoundException;
import com.mclods.store_app.mappers.UserMapper;
import com.mclods.store_app.services.AddressService;
import com.mclods.store_app.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "User Api", description = "Operations related to user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    private final UserService userService;

    private final AddressService addressService;

    public UserController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @GetMapping(path = "/")
    @Operation(summary = "Homepage", description = "Homepage")
    public String homepage() {
        return "Welcome to store-app";
    }

    @PostMapping(path = "/users")
    @Operation(summary = "Create user", description = "Create a new user")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        User userToSave = userMapper.mapCreateUserRequestToUser(createUserRequest);
        User savedUser = userService.save(userToSave);
        return new ResponseEntity<>(userMapper.mapUserToUserResponse(savedUser), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users/{id}")
    @Operation(summary = "Find one user", description = "Find one user by id")
    public ResponseEntity<UserResponse> findOneUser(@PathVariable("id") Long id) {
        Optional<User> foundUser = userService.findOne(id);
        return foundUser.map((user) ->
                new ResponseEntity<>(userMapper.mapUserToUserResponse(user), HttpStatus.OK))
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/users")
    @Operation(summary = "Find all users", description = "Find all users")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        List<UserResponse> foundUsers = new ArrayList<>();
        userService.findAll().forEach(user -> foundUsers.add(userMapper.mapUserToUserResponse(user)));
        return new ResponseEntity<>(foundUsers, HttpStatus.OK);
    }

    @PutMapping(path = "/users/{id}")
    @Operation(summary = "Full update user", description = "Full update all the details about an user by id")
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

    @PatchMapping(path = "/users/{id}")
    @Operation(summary = "Partial update user", description = "Partially update the needed details about an user by id")
    public ResponseEntity<UserResponse> partialUpdateUser(
            @PathVariable("id") Long id,
            @RequestBody @Valid PartialUpdateUserRequest partialUpdateUserRequest
    ) throws UserNotFoundException, AddressHasMissingFieldsException {
        if(!userService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User userToUpdate = userMapper.mapPartialUpdateUserRequestToUser(partialUpdateUserRequest);
        User updatedUser = userService.partialUpdateUser(id, userToUpdate);
        return new ResponseEntity<>(userMapper.mapUserToUserResponse(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete user", description = "Delete an user by id")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        if(!userService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{id}/addresses")
    @Operation(summary = "Find all addresses", description = "Find all addresses belonging to a user by user id")
    public ResponseEntity<List<UserResponse.UserAddress>> findAllAddresses(@PathVariable("id") Long id) {
        if(!userService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<UserResponse.UserAddress> foundAddresses = new ArrayList<>();
        addressService.findAllByUserId(id)
                .forEach(address -> foundAddresses.add(userMapper.mapAddressToUserAddress(address)));
        return new ResponseEntity<>(foundAddresses, HttpStatus.OK);
    }
}
