package com.myproject.nabe_bank.modules.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public class GoogleSignInRequest {
    @NotBlank(message = "idToken must not be empty")
    private String idToken;

    public GoogleSignInRequest(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
