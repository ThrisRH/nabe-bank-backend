package com.myproject.nabe_bank.modules.auth.service;

import com.myproject.nabe_bank.modules.auth.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse googleSignIn(String idToken);
}
