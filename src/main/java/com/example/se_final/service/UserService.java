package com.example.se_final.service;

import com.example.se_final.dto.UserDto;
import com.example.se_final.mapper.UserMapper;
import com.example.se_final.model.Permission;
import com.example.se_final.model.User;
import com.example.se_final.repository.PermissionRepository;
import com.example.se_final.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository; // Чтобы выдавать роль при регистрации

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) return user;
        throw new UsernameNotFoundException("User not found");
    }

    // РЕГИСТРАЦИЯ
    public UserDto registerUser(UserDto dto) {
        User user = userMapper.toEntity(dto);

        // 1. Хешируем пароль
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // 2. Выдаем стандартную роль ROLE_USER
        Permission userRole = permissionRepository.findByName("ROLE_USER");
        if (userRole != null) {
            user.setPermissions(Collections.singletonList(userRole));
        }

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserDto getById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElse(null));
    }
}