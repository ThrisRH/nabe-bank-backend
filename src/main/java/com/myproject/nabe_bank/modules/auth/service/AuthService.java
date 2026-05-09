package com.myproject.nabe_bank.modules.auth.service;

import com.myproject.nabe_bank.modules.auth.dto.response.AuthResponse;
import com.myproject.nabe_bank.modules.user.dto.response.UserResponse;

public interface AuthService {
    AuthResponse googleSignIn(String idToken);

    UserResponse getMe(Long userId);
}
