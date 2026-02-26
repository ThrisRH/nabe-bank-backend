package com.myproject.nabe_bank.user.dto.response;

import java.time.LocalDateTime;

import com.myproject.nabe_bank.user.UserRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String avatar;
    private Boolean enabled;
    private Boolean locked;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public UserResponse(Long id, String name, String email, String avatar, Boolean enabled, Boolean locked,
            UserRole role, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.enabled = enabled;
        this.locked = locked;
        this.role = role;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
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
