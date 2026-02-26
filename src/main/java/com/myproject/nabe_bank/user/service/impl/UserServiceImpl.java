package com.myproject.nabe_bank.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myproject.nabe_bank.user.User;
import com.myproject.nabe_bank.user.UserRepository;
import com.myproject.nabe_bank.user.dto.request.CreateUserRequest;
import com.myproject.nabe_bank.user.dto.request.PatchUserRequest;
import com.myproject.nabe_bank.user.dto.request.UpdateUserRequest;
import com.myproject.nabe_bank.user.dto.response.UserResponse;
import com.myproject.nabe_bank.user.mapper.UserMapper;
import com.myproject.nabe_bank.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user = userRepository.save(user);

        return userMapper.toRespponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new IllegalArgumentException("No users found!");
        }
        return users.stream()
                .map(user -> userMapper.toRespponse(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return userMapper.toRespponse(user);
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return userMapper.toRespponse(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.delete(user);
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setEmail(request.getEmail());
        user.setName(request.getName());

        user = userRepository.save(user);

        return userMapper.toRespponse(user);
    }

    @Override
    public UserResponse updateUserProfile(Long id, PatchUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        return userMapper.toRespponse(user);
    }

}