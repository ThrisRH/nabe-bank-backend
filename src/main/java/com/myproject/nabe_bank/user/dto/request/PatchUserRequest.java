package com.myproject.nabe_bank.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PatchUserRequest {
    @NotBlank(message = "Name must not be empty")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    public PatchUserRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
