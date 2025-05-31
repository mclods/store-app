package com.mclods.store_app.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
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

import java.util.List;

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
        CreateUserRequest createUserRequest = TestDataUtils.testCreateUserRequestA();
        String createUserRequestJson = objectMapper.writeValueAsString(createUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    @DisplayName("Test create user succeeds and returns the created user")
    void testCreateUserSucceedsAndReturnsTheCreatedUser() throws Exception {
        CreateUserRequest createUserRequest = TestDataUtils.testCreateUserRequestA();
        String createUserRequestJson = objectMapper.writeValueAsString(createUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(createUserRequest.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(createUserRequest.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value(createUserRequest.getPassword())
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
    @DisplayName("Test create user fails with status code 400 Bad Request when request body has missing fields")
    void testCreateUserFailsWithStatusCode400BadRequestWhenRequestBodyHasMissingFields() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password")
                        .value("password cannot be null")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name")
                        .value("user name cannot be null")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email")
                        .value("email cannot be null")
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
                MockMvcResultMatchers.jsonPath("$.addresses[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].street").value(user.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(user.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].zip").value(user.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(user.getAddresses().get(0).getState())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].street").value(user.getAddresses().get(1).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].city").value(user.getAddresses().get(1).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].zip").value(user.getAddresses().get(1).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].state").value(user.getAddresses().get(1).getState())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tags").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tags").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.bio").value(user.getProfile().getBio())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.phoneNumber").value(user.getProfile().getPhoneNumber())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.dateOfBirth").value(user.getProfile().getDateOfBirth().toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.loyaltyPoints").value(user.getProfile().getLoyaltyPoints())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.wishlist").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.wishlist").isEmpty()
        );
    }

    @Test
    @DisplayName("Test full update user succeeds with status code 200 Ok")
    void testFullUpdateUserSucceedsWithStatusCode200Ok() throws Exception {
        User testUser = TestDataUtils.testUserA();
        userRepository.save(testUser);

        FullUpdateUserRequest fullUpdateUserRequest = TestDataUtils.testFullUpdateUserRequestA();
        String fullUpdateUserRequestJson = objectMapper.writeValueAsString(fullUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fullUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Test full update user fails with status code 404 Not Found when user does not exist")
    void testFullUpdateUserFailsWithStatusCode404NotFoundWhenUserDoesNotExist() throws Exception {
        FullUpdateUserRequest fullUpdateUserRequest = TestDataUtils.testFullUpdateUserRequestA();
        String fullUpdateUserRequestJson = objectMapper.writeValueAsString(fullUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/users/%d", 9999))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fullUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @DisplayName("Test full update user removes addresses and profile when request does not contain it")
    void testFullUpdateUserRemovesAddressesAndProfileWhenRequestDoesNotContainIt() throws Exception {
        User testUser = TestDataUtils.testUserWithAddressAndProfileA();
        userRepository.save(testUser);

        FullUpdateUserRequest fullUpdateUserRequest = TestDataUtils.testFullUpdateUserRequestA();
        String fullUpdateUserRequestJson = objectMapper.writeValueAsString(fullUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fullUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testUser.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(fullUpdateUserRequest.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(fullUpdateUserRequest.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value(fullUpdateUserRequest.getPassword())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tags").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tags").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.wishlist").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.wishlist").isEmpty()
        );
    }

    @Test
    @DisplayName("Test full update user updates address if address id is provided")
    void testFullUpdateUserUpdatesAddressIfAddressIdIsProvided() throws Exception {
        User testUser = TestDataUtils.testUserWithAddressAndProfileB();
        userRepository.save(testUser);

        Long savedUserAddressId = testUser.getAddresses().get(0).getId();

        FullUpdateUserRequest fullUpdateUserRequest = TestDataUtils.testFullUpdateUserRequestWithAddressAndProfileB();
        FullUpdateUserRequest.FullUpdateUserAddress fullUpdateUserAddress = fullUpdateUserRequest.getAddresses().get(0);
        fullUpdateUserAddress.setId(savedUserAddressId);

        fullUpdateUserRequest.setAddresses(List.of(fullUpdateUserAddress));

        String fullUpdateUserRequestJson = objectMapper.writeValueAsString(fullUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fullUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testUser.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].id").value(savedUserAddressId)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].street").value(fullUpdateUserRequest.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(fullUpdateUserRequest.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].zip").value(fullUpdateUserRequest.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(fullUpdateUserRequest.getAddresses().get(0).getState())
        );
    }
}
