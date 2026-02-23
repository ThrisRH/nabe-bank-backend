package com.myproject.nabe_bank.user.service;

import java.util.List;

import com.myproject.nabe_bank.user.dto.request.CreateUserRequest;
import com.myproject.nabe_bank.user.dto.request.PatchUserRequest;
import com.myproject.nabe_bank.user.dto.request.UpdateUserRequest;
import com.myproject.nabe_bank.user.dto.response.UserResponse;

public interface UserService {
    // Query
    UserResponse getById(Long id);

    UserResponse getByEmail(String email);

    List<UserResponse> getAll();

    // User Profile
    UserResponse updateUserProfile(Long id, PatchUserRequest request);

    // Admin action
    UserResponse create(CreateUserRequest request);

    UserResponse update(Long id, UpdateUserRequest request);

    void delete(Long id);

}
