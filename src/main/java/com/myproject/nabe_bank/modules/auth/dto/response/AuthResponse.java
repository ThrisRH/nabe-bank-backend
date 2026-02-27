package com.myproject.nabe_bank.modules.auth.dto.response;

import java.time.LocalDateTime;

import com.myproject.nabe_bank.modules.user.UserRole;

public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String email;
    private UserRole role;
    private LocalDateTime createdDate;

    public AuthResponse(String accessToken, Long userId, String email, UserRole role, LocalDateTime createdDate) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.createdDate = createdDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
