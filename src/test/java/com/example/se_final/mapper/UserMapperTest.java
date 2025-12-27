package com.example.se_final.mapper;

import com.example.se_final.dto.UserDto;
import com.example.se_final.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void toDtoTest() {
        User user = new User();
        user.setId(1L);
        user.setUsername("nurken");
        user.setEmail("nurken@gmail.com");
        user.setPassword("asdsadsa");

        UserDto dto = userMapper.toDto(user);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(user.getId(), dto.getId());
        Assertions.assertEquals(user.getUsername(), dto.getUsername());
        Assertions.assertEquals(user.getEmail(), dto.getEmail());
        Assertions.assertEquals(user.getPassword(), dto.getPassword());
    }

    @Test
    void toEntityTest() {
        UserDto dto = new UserDto(2L, "user_dto", "dto@test.com", "pass123");
        User user = userMapper.toEntity(dto);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(dto.getId(), user.getId());
        Assertions.assertEquals(dto.getUsername(), user.getUsername());
        Assertions.assertEquals(dto.getEmail(), user.getEmail());
        Assertions.assertEquals(dto.getPassword(), user.getPassword());

        Assertions.assertNull(user.getReviews());
        Assertions.assertNull(user.getPermissions());
    }
}