package com.mclods.store_app.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mclods.store_app.domain.entities.User;
import com.mclods.store_app.repositories.UserRepository;
import com.mclods.store_app.utils.TestDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("Test create user succeeds with status code 201 Created")
    void testCreateUserSucceedsWithStatusCode201Created() throws Exception {
        User user = TestDataUtils.testUserA();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    @DisplayName("Test create user succeeds and returns the created user")
    void testCreateUserSucceedsAndReturnsTheCreatedUser() throws Exception {
        User user = TestDataUtils.testUserA();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(user.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value(user.getPassword())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tags").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tags").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile").doesNotExist()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.wishlist").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.wishlist").isEmpty()
        );
    }

    @Test
    @DisplayName("Test find user succeeds with status code 200 Ok when user is found")
    void testFindUserSucceedsWithStatusCode200OkWhenUserIsFound() throws Exception {
        User user = TestDataUtils.testUserA();
        userRepository.save(user);

        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format("/users/%d", user.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Test find user fails with status code 404 Not Found when user does not exist")
    void testFindUserFailsWithStatusCode404NotFoundWhenUserDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/100")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @DisplayName("Test find user succeeds and returns the user when user is found")
    void testFindUserSucceedsAndReturnsTheUserWhenUserIsFound() throws Exception {
        User user = TestDataUtils.testUserWithAddressAndProfileA();
        userRepository.save(user);

        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format("/users/%d", user.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(user.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value(user.getPassword())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0]").value(user.getAddresses().get(0))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tags").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tags").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.wishlist").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.wishlist").isEmpty()
        );
    }
}
