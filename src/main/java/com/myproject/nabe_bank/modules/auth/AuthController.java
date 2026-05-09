package com.myproject.nabe_bank.modules.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.myproject.nabe_bank.modules.auth.dto.request.GoogleSignInRequest;
import com.myproject.nabe_bank.modules.auth.dto.response.AuthResponse;
import com.myproject.nabe_bank.modules.auth.service.AuthService;
import com.myproject.nabe_bank.modules.user.dto.response.UserResponse;
import com.myproject.nabe_bank.core.exception.ApiResponse;
import com.myproject.nabe_bank.core.response.ResponseCode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyToken(@RequestBody GoogleSignInRequest request) {
        AuthResponse response = authService.googleSignIn(request.getIdToken());
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.badRequest().build();
        }
        Long userId = Long.parseLong(authentication.getName());
        UserResponse response = authService.getMe(userId);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, response));
    }
}
