package com.myproject.nabe_bank.auth.service;

import com.myproject.nabe_bank.auth.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse googleSignIn(String idToken);
}
