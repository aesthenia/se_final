package com.example.se_final.service;

import com.example.se_final.dto.UserDto;
import com.example.se_final.model.Permission;
import com.example.se_final.repository.PermissionRepository;
import com.example.se_final.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        if (permissionRepository.findByName("ROLE_USER") == null) {
            Permission userRole = new Permission();
            userRole.setName("ROLE_USER");
            permissionRepository.save(userRole);
        }
    }

    @Test
    void registerUserTest() {
        UserDto dto = new UserDto();
        dto.setUsername("test_user");
        dto.setEmail("test@example.com");
        dto.setPassword("raw_password");

        UserDto saved = userService.registerUser(dto);

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("test_user", saved.getUsername());

        var entity = userRepository.findById(saved.getId()).orElseThrow();
        Assertions.assertTrue(passwordEncoder.matches("raw_password", entity.getPassword()));
    }

    @Test
    void loadUserByUsernameTest() {
        UserDto dto = new UserDto(null, "login", "mail@mail.com", "pass");
        userService.registerUser(dto);

        var userDetails = userService.loadUserByUsername("login");
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals("login", userDetails.getUsername());
    }

    @Test
    void getByIdTest() {
        UserDto dto = new UserDto(null, "id_test", "id@mail.com", "pass");
        UserDto saved = userService.registerUser(dto);

        UserDto found = userService.getById(saved.getId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("id_test", found.getUsername());
    }
}