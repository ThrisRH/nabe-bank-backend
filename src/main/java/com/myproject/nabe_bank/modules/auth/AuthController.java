package com.myproject.nabe_bank.modules.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.nabe_bank.modules.auth.dto.request.GoogleSignInRequest;
import com.myproject.nabe_bank.modules.auth.dto.response.AuthResponse;
import com.myproject.nabe_bank.modules.auth.service.AuthService;
import com.myproject.nabe_bank.core.exception.ApiResponse;

import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok(new ApiResponse<>(200, "SUCCESS", "Update field successfully", response));
    }

}
