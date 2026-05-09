package com.myproject.nabe_bank.modules.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.nabe_bank.core.exception.ApiResponse;
import com.myproject.nabe_bank.core.response.ResponseCode;
import com.myproject.nabe_bank.modules.user.dto.request.CreateUserRequest;
import com.myproject.nabe_bank.modules.user.dto.request.PatchUserRequest;
import com.myproject.nabe_bank.modules.user.dto.request.UpdateUserRequest;
import com.myproject.nabe_bank.modules.user.dto.response.UserResponse;
import com.myproject.nabe_bank.modules.user.service.UserService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<UserResponse> response = userService.getAll();
        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getById(
            @PathVariable Long id) {
        UserResponse response = userService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.CREATED, null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> patch(@PathVariable Long id,
            @RequestBody PatchUserRequest request) {
        UserResponse response = userService.updateUserProfile(id, request);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, response));
    }
}
