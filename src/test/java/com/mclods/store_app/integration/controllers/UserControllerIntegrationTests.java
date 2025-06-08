package com.mclods.store_app.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mclods.store_app.domain.dtos.user.CreateUserRequest;
import com.mclods.store_app.domain.dtos.user.FullUpdateUserRequest;
import com.mclods.store_app.domain.dtos.user.PartialUpdateUserRequest;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

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
    @DisplayName("Test create user creates user with address and profile if provided")
    void testCreateUserCreatesUserWithAddressAndProfileIfProvided() throws Exception {
        CreateUserRequest createUserRequest = TestDataUtils.testCreateUserRequestWithAddressAndProfileA();
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
                MockMvcResultMatchers.jsonPath("$.addresses", hasSize(2))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].street").value(createUserRequest.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(createUserRequest.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].zip").value(createUserRequest.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(createUserRequest.getAddresses().get(0).getState())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].street").value(createUserRequest.getAddresses().get(1).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].city").value(createUserRequest.getAddresses().get(1).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].zip").value(createUserRequest.getAddresses().get(1).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].state").value(createUserRequest.getAddresses().get(1).getState())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.bio").value(createUserRequest.getProfile().getBio())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.phoneNumber").value(createUserRequest.getProfile().getPhoneNumber())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.dateOfBirth").value(createUserRequest.getProfile().getDateOfBirth().toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.loyaltyPoints").value(createUserRequest.getProfile().getLoyaltyPoints())
        );
    }

    @Test
    @DisplayName("Test create user fails with status code 400 Bad Request when user body has missing fields")
    void testCreateUserFailsWithStatusCode400BadRequestWhenUserBodyHasMissingFields() throws Exception {
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
    @DisplayName("Test create user fails with status code 400 Bad Request when address body has missing fields")
    void testCreateUserFailsWithStatusCode400BadRequestWhenAddressBodyHasMissingFields() throws Exception {
        String mockRequest = """
                {
                    "name": "Zachary Levi",
                    "email": "zacharys.mail.@mail.com",
                    "password": "levi12",
                    "addresses": [{}]
                }
                """;

        String expectedResponse = "{\"addresses[0].city\":\"city cannot be null\",\"addresses[0].state\":\"state cannot be null\",\"addresses[0].street\":\"street cannot be null\",\"addresses[0].zip\":\"zip cannot be null\"}";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockRequest)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.content().string(expectedResponse)
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
    @DisplayName("Test full update user updates user and adds new address and profile if provided")
    void testFullUpdateUserUpdatesUserAndAddsNewAddressAndProfileIfProvided() throws Exception {
        User testUser = TestDataUtils.testUserA();
        userRepository.save(testUser);

        FullUpdateUserRequest fullUpdateUserRequest = TestDataUtils.testFullUpdateUserRequestWithAddressAndProfileA();
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
                MockMvcResultMatchers.jsonPath("$.addresses", hasSize(1))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].street").value(fullUpdateUserRequest.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(fullUpdateUserRequest.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].zip").value(fullUpdateUserRequest.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(fullUpdateUserRequest.getAddresses().get(0).getState())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.bio").value(fullUpdateUserRequest.getProfile().getBio())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.phoneNumber").value(fullUpdateUserRequest.getProfile().getPhoneNumber())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.dateOfBirth").value(fullUpdateUserRequest.getProfile().getDateOfBirth().toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.loyaltyPoints").value(fullUpdateUserRequest.getProfile().getLoyaltyPoints())
        );
    }

    @Test
    @DisplayName("Test full update user updates user and removes addresses and profile when its not provided")
    void testFullUpdateUserUpdatesUserAndRemovesAddressesAndProfileWhenItsNotProvided() throws Exception {
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
    @DisplayName("Test full update user updates existing address if address id is provided")
    void testFullUpdateUserUpdatesExistingAddressIfAddressIdIsProvided() throws Exception {
        User testUser = TestDataUtils.testUserWithAddressAndProfileB();
        userRepository.save(testUser);

        Long savedUserAddressId = testUser.getAddresses().get(0).getId();

        FullUpdateUserRequest fullUpdateUserRequest = TestDataUtils.testFullUpdateUserRequestA();

        FullUpdateUserRequest.FullUpdateUserAddress address = TestDataUtils.testFullUpdateUserAddressA();
        address.setId(savedUserAddressId);

        fullUpdateUserRequest.setAddresses(List.of(address));

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
                MockMvcResultMatchers.jsonPath("$.addresses", hasSize(1))
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

    @Test
    @DisplayName("Test full update user creates new address and removes the old one if correct address id is not provided")
    void testFullUpdateUserCreatesNewAddressAndRemovesTheOldOneIfCorrectAddressIdIsNotProvided() throws Exception {
        User testUser = TestDataUtils.testUserWithAddressAndProfileB();
        userRepository.save(testUser);

        Long savedUserAddressId = testUser.getAddresses().get(0).getId();

        FullUpdateUserRequest fullUpdateUserRequest = TestDataUtils.testFullUpdateUserRequestA();

        FullUpdateUserRequest.FullUpdateUserAddress address = TestDataUtils.testFullUpdateUserAddressA();
        address.setId(savedUserAddressId + 9999);

        fullUpdateUserRequest.setAddresses(List.of(address));

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
                MockMvcResultMatchers.jsonPath("$.addresses", hasSize(1))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].id", not(savedUserAddressId))
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

    @Test
    @DisplayName("Test full update user creates new address if provided id belongs to address of a different user")
    void testFullUpdateUserCreatesNewAddressIfProvidedIdBelongsToAddressOfADifferentUser() throws Exception {
        User testUserA = TestDataUtils.testUserWithAddressAndProfileA();
        User testUserB = TestDataUtils.testUserWithAddressAndProfileB();

        userRepository.save(testUserA);
        userRepository.save(testUserB);

        Long savedUserAddressIdOfADifferentUser = testUserA.getAddresses().get(0).getId();

        FullUpdateUserRequest fullUpdateUserRequest = TestDataUtils.testFullUpdateUserRequestA();

        FullUpdateUserRequest.FullUpdateUserAddress address = TestDataUtils.testFullUpdateUserAddressA();
        address.setId(savedUserAddressIdOfADifferentUser);

        fullUpdateUserRequest.setAddresses(List.of(address));

        String fullUpdateUserRequestJson = objectMapper.writeValueAsString(fullUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/users/%d", testUserB.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fullUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testUserB.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses", hasSize(1))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].id", not(savedUserAddressIdOfADifferentUser))
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

    @Test
    @DisplayName("Test full update user updates profile if provided")
    void testFullUpdateUserUpdatesProfileIfProvided() throws Exception {
        User testUser = TestDataUtils.testUserWithAddressAndProfileB();
        userRepository.save(testUser);

        FullUpdateUserRequest fullUpdateUserRequest = TestDataUtils.testFullUpdateUserRequestWithAddressAndProfileA();
        String fullUpdateUserRequestJson = objectMapper.writeValueAsString(fullUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fullUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.bio").value(fullUpdateUserRequest.getProfile().getBio())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.phoneNumber").value(fullUpdateUserRequest.getProfile().getPhoneNumber())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.dateOfBirth").value(fullUpdateUserRequest.getProfile().getDateOfBirth().toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.loyaltyPoints").value(fullUpdateUserRequest.getProfile().getLoyaltyPoints())
        );
    }

    @Test
    @DisplayName("Test full update user fails with status code 400 Bad Request when user body has missing fields")
    void testFullUpdateUserFailsWithStatusCode400BadRequestWhenUserBodyHasMissingFields() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/1")
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
    @DisplayName("Test full update user fails with status code 400 Bad Request when address body has missing fields")
    void testFullUpdateUserFailsWithStatusCode400BadRequestWhenAddressBodyHasMissingFields() throws Exception {
        String mockRequest = """
                {
                    "name": "Zachary Levi",
                    "email": "zacharys.mail.@mail.com",
                    "password": "levi12",
                    "addresses": [{}]
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockRequest)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$['addresses[0].street']").value("street cannot be null")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$['addresses[0].city']").value("city cannot be null")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$['addresses[0].zip']").value("zip cannot be null")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$['addresses[0].state']").value("state cannot be null")
        );
    }

    @Test
    @DisplayName("Test full update user fails with status code 400 Bad Request when address body has duplicate ids")
    void testFullUpdateUserFailsWithStatusCode400BadRequestWhenAddressBodyHasDuplicateIds() throws Exception {
        FullUpdateUserRequest fullUpdateUserRequest = TestDataUtils.testFullUpdateUserRequestA();

        FullUpdateUserRequest.FullUpdateUserAddress addressA = TestDataUtils.testFullUpdateUserAddressA();
        FullUpdateUserRequest.FullUpdateUserAddress addressB = TestDataUtils.testFullUpdateUserAddressB();

        addressA.setId(1L);
        addressB.setId(1L);

        fullUpdateUserRequest.setAddresses(Arrays.asList(addressA, addressB));

        String testUserJson = objectMapper.writeValueAsString(fullUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").value("Address Ids must be unique")
        );
    }

    @Test
    @DisplayName("Test partial update user succeeds with status code 200 Ok")
    void testPartialUpdateUserSucceedsWithStatusCode200Ok() throws Exception {
        User testUser = TestDataUtils.testUserA();
        userRepository.save(testUser);

        PartialUpdateUserRequest partialUpdateUserRequest = TestDataUtils.testPartialUpdateUserRequestA();
        String partialUpdateUserRequestJson = objectMapper.writeValueAsString(partialUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Test partial update user fails with status code 404 Not Found when user does not exist")
    void testPartialUpdateUserFailsWithStatusCode404NotFoundWhenUserDoesNotExist() throws Exception {
        PartialUpdateUserRequest partialUpdateUserRequest = TestDataUtils.testPartialUpdateUserRequestA();
        String partialUpdateUserRequestJson = objectMapper.writeValueAsString(partialUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/users/%d", 9999))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @DisplayName("Test partial update user partially updates the user")
    void testPartialUpdateUserPartiallyUpdatesTheUser() throws Exception {
        User testUser = TestDataUtils.testUserA();
        userRepository.save(testUser);

        PartialUpdateUserRequest partialUpdateUserRequest = TestDataUtils.testPartialUpdateUserRequestA();
        String partialUpdateUserRequestJson = objectMapper.writeValueAsString(partialUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testUser.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(partialUpdateUserRequest.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(testUser.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value(testUser.getPassword())
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
    @DisplayName("Test partial update user updates user and adds new address and profile if provided")
    void testPartialUpdateUserUpdatesUserAndAddsNewAddressAndProfileIfProvided() throws Exception {
        User testUser = TestDataUtils.testUserA();
        userRepository.save(testUser);

        PartialUpdateUserRequest partialUpdateUserRequest = TestDataUtils.testPartialUpdateUserRequestWithAddressAndProfileA();
        String partialUpdateUserRequestJson = objectMapper.writeValueAsString(partialUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testUser.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses", hasSize(1))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].street").value(partialUpdateUserRequest.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(partialUpdateUserRequest.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].zip").value(partialUpdateUserRequest.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(partialUpdateUserRequest.getAddresses().get(0).getState())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.bio").value(partialUpdateUserRequest.getProfile().getBio())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.phoneNumber").value(partialUpdateUserRequest.getProfile().getPhoneNumber())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.dateOfBirth").value(partialUpdateUserRequest.getProfile().getDateOfBirth().toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.loyaltyPoints").value(partialUpdateUserRequest.getProfile().getLoyaltyPoints())
        );
    }

    @Test
    @DisplayName("Test partial update user creates new address if address id is not provided")
    void testPartialUpdateUserCreatesNewAddressIfAddressIdIsNotProvided() throws Exception {
        User testUser = TestDataUtils.testUserWithAddressAndProfileB();
        userRepository.save(testUser);

        PartialUpdateUserRequest partialUpdateUserRequest = TestDataUtils.testPartialUpdateUserRequestA();

        PartialUpdateUserRequest.PartialUpdateUserAddress address = TestDataUtils.testPartialUpdateUserAddressA();
        address.setId(null);

        partialUpdateUserRequest.setAddresses(List.of(address));

        String partialUpdateUserRequestJson = objectMapper.writeValueAsString(partialUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testUser.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(partialUpdateUserRequest.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses", hasSize(2))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].id").value(testUser.getAddresses().get(0).getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].street").value(testUser.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(testUser.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].zip").value(testUser.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(testUser.getAddresses().get(0).getState())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].street").value(partialUpdateUserRequest.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].city").value(partialUpdateUserRequest.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].zip").value(partialUpdateUserRequest.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].state").value(partialUpdateUserRequest.getAddresses().get(0).getState())
        );
    }

    @Test
    @DisplayName("Test partial update user partially updates existing address if address id is provided")
    void testPartialUpdateUserPartiallyUpdatesExistingAddressIfAddressIdIsProvided() throws Exception {
        User testUser = TestDataUtils.testUserWithAddressAndProfileB();
        userRepository.save(testUser);

        Long savedUserAddressId = testUser.getAddresses().get(0).getId();

        PartialUpdateUserRequest partialUpdateUserRequest = TestDataUtils.testPartialUpdateUserRequestA();

        PartialUpdateUserRequest.PartialUpdateUserAddress address = new PartialUpdateUserRequest.PartialUpdateUserAddress(
                savedUserAddressId,
                "Camac Street",
                null,
                null,
                null
        );
        partialUpdateUserRequest.setAddresses(List.of(address));

        String partialUpdateUserRequestJson = objectMapper.writeValueAsString(partialUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testUser.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses", hasSize(1))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].id").value(savedUserAddressId)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].street").value("Camac Street")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(testUser.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].zip").value(testUser.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(testUser.getAddresses().get(0).getState())
        );
    }

    @Test
    @DisplayName("Test partial update user creates new address if correct address id is not provided")
    void testPartialUpdateUserCreatesNewAddressIfCorrectAddressIdIsNotProvided() throws Exception {
        User testUser = TestDataUtils.testUserWithAddressAndProfileB();
        userRepository.save(testUser);

        Long savedUserAddressId = testUser.getAddresses().get(0).getId();

        PartialUpdateUserRequest partialUpdateUserRequest = TestDataUtils.testPartialUpdateUserRequestA();

        PartialUpdateUserRequest.PartialUpdateUserAddress address = TestDataUtils.testPartialUpdateUserAddressA();
        address.setId(savedUserAddressId + 9999);

        partialUpdateUserRequest.setAddresses(List.of(address));

        String partialUpdateUserRequestJson = objectMapper.writeValueAsString(partialUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testUser.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses", hasSize(2))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].id").value(savedUserAddressId)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].street").value(testUser.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(testUser.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].zip").value(testUser.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(testUser.getAddresses().get(0).getState())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].street").value(partialUpdateUserRequest.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].city").value(partialUpdateUserRequest.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].zip").value(partialUpdateUserRequest.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].state").value(partialUpdateUserRequest.getAddresses().get(0).getState())
        );
    }

    @Test
    @DisplayName("Test partial update user creates new address if provided id belongs to address of a different user")
    void testPartialUpdateUserCreatesNewAddressIfProvidedIdBelongsToAddressOfADifferentUser() throws Exception {
        User testUserA = TestDataUtils.testUserWithAddressAndProfileA();
        User testUserB = TestDataUtils.testUserWithAddressAndProfileB();

        userRepository.save(testUserA);
        userRepository.save(testUserB);

        Long savedUserAddressIdOfADifferentUser = testUserA.getAddresses().get(0).getId();

        PartialUpdateUserRequest partialUpdateUserRequest = TestDataUtils.testPartialUpdateUserRequestA();

        PartialUpdateUserRequest.PartialUpdateUserAddress address = TestDataUtils.testPartialUpdateUserAddressA();
        address.setId(savedUserAddressIdOfADifferentUser);

        partialUpdateUserRequest.setAddresses(List.of(address));

        String partialUpdateUserRequestJson = objectMapper.writeValueAsString(partialUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/users/%d", testUserB.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testUserB.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses", hasSize(2))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].id").value(testUserB.getAddresses().get(0).getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].street").value(testUserB.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(testUserB.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].zip").value(testUserB.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(testUserB.getAddresses().get(0).getState())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].id", not(savedUserAddressIdOfADifferentUser))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].street").value(partialUpdateUserRequest.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].city").value(partialUpdateUserRequest.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].zip").value(partialUpdateUserRequest.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.addresses[1].state").value(partialUpdateUserRequest.getAddresses().get(0).getState())
        );
    }

    @Test
    @DisplayName("Test partial update user updates profile if provided")
    void testPartialUpdateUserUpdatesProfileIfProvided() throws Exception {
        User testUser = TestDataUtils.testUserWithAddressAndProfileB();
        userRepository.save(testUser);

        PartialUpdateUserRequest partialUpdateUserRequest = TestDataUtils.testPartialUpdateUserRequestWithAddressAndProfileA();
        String partialUpdateUserRequestJson = objectMapper.writeValueAsString(partialUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.bio").value(partialUpdateUserRequest.getProfile().getBio())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.phoneNumber").value(testUser.getProfile().getPhoneNumber())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.dateOfBirth").value(partialUpdateUserRequest.getProfile().getDateOfBirth().toString())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.profile.loyaltyPoints").value(partialUpdateUserRequest.getProfile().getLoyaltyPoints())
        );
    }

    @Test
    @DisplayName("Test partial update user fails with status code 400 Bad Request if address id is not provided and address has missing fields")
    void testPartialUpdateUserFailsWithStatusCode400BadRequestIfAddressIdIsNotProvidedAndAddressHasMissingFields() throws Exception {
        User testUser = TestDataUtils.testUserWithAddressAndProfileB();
        userRepository.save(testUser);

        PartialUpdateUserRequest partialUpdateUserRequest = TestDataUtils.testPartialUpdateUserRequestA();

        PartialUpdateUserRequest.PartialUpdateUserAddress address = new PartialUpdateUserRequest.PartialUpdateUserAddress(
                null,
                null,
                "Tel Aviv - Jaffa",
                "123-456",
                "Israel"
        );

        partialUpdateUserRequest.setAddresses(List.of(address));

        String partialUpdateUserRequestJson = objectMapper.writeValueAsString(partialUpdateUserRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("/users/%d", testUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateUserRequestJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.error").value("Address (id=null, street=null, city=Tel Aviv - Jaffa, zip=123-456, state=Israel) has missing fields.")
        );
    }

    @Test
    @DisplayName("Test find all users succeeds with status code 200 and returns all users")
    void testFindAllUsersSucceedsWithStatusCode200AndReturnsAllUsers() throws Exception {
        User userA = TestDataUtils.testUserA();
        User userB = TestDataUtils.testUserB();
        userRepository.save(userA);
        userRepository.save(userB);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].name").value(userA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].email").value(userA.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].password").value(userA.getPassword())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].addresses").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].profile").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[1].name").value(userB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[1].email").value(userB.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[1].password").value(userB.getPassword())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[1].addresses").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[1].addresses").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[1].profile").isEmpty()
        );
    }

    @Test
    @DisplayName("Test find all users returns empty list when no users exist")
    void testFindAllUsersReturnsEmptyListWhenNoUsersExist() throws Exception {
        String expectedJson = "[]";

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.content().string(expectedJson)
        );
    }

    @Test
    @DisplayName("Test delete user deletes user and returns status code 204 No Content")
    void testDeleteUserDeletesUserAndReturnsStatusCode204NoContent() throws Exception {
        User savedUser = userRepository.save(TestDataUtils.testUserA());
        Long savedUserId = savedUser.getId();

        mockMvc.perform(
                MockMvcRequestBuilders.delete(String.format("/users/%d", savedUserId))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

        // Assert user does not exist
        Optional<User> foundUser = userRepository.findById(savedUserId);
        assertThat(foundUser).isEmpty();
    }

    @Test
    @DisplayName("Test delete user fails with status code 404 Not Found when user does not exist")
    void testDeleteUserFailsWithStatusCode404NotFoundWhenUserDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @DisplayName("Test find all addresses succeeds with status code 200 and finds all addresses belonging to a user")
    void testFindAllAddressesSucceedsWithStatusCode200AndFindsAllAddressesBelongingToAUser() throws Exception {
        User testUserA = TestDataUtils.testUserWithAddressAndProfileA(),
                testUserB = TestDataUtils.testUserWithAddressAndProfileB();

        userRepository.save(testUserA);
        userRepository.save(testUserB);

        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format("/users/%d/addresses", testUserA.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(testUserA.getAddresses().get(0).getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].street").value(testUserA.getAddresses().get(0).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].city").value(testUserA.getAddresses().get(0).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].zip").value(testUserA.getAddresses().get(0).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].state").value(testUserA.getAddresses().get(0).getState())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].id").value(testUserA.getAddresses().get(1).getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].street").value(testUserA.getAddresses().get(1).getStreet())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].city").value(testUserA.getAddresses().get(1).getCity())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].zip").value(testUserA.getAddresses().get(1).getZip())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].state").value(testUserA.getAddresses().get(1).getState())
        );
    }
}
