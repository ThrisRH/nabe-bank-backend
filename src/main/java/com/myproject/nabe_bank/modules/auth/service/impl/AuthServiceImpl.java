package com.myproject.nabe_bank.modules.auth.service.impl;

import org.springframework.stereotype.Service;

import com.myproject.nabe_bank.modules.auth.dto.response.AuthResponse;
import com.myproject.nabe_bank.modules.auth.service.AuthService;
import com.myproject.nabe_bank.core.security.jwt.JwtProvider;
import com.myproject.nabe_bank.core.security.oauth.GooglePayload;
import com.myproject.nabe_bank.core.security.oauth.GoogleTokenVerifier;
import com.myproject.nabe_bank.modules.user.User;
import com.myproject.nabe_bank.modules.user.UserRepository;
import com.myproject.nabe_bank.modules.user.UserRole;
import com.myproject.nabe_bank.modules.user.dto.response.UserResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final GoogleTokenVerifier googleTokenVerifier;

    @Override
    public AuthResponse googleSignIn(String idToken) {
        GooglePayload payload = googleTokenVerifier.verify(idToken);

        String email = payload.getEmail();
        String googleId = payload.getSub();
        String name = payload.getName();
        String avatar = payload.getPicture();

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setGoogleId(googleId);
            newUser.setAvatar(avatar);
            newUser.setRole(UserRole.EMPLOYEE);
            return userRepository.save(newUser);
        });
        String accessToken = jwtProvider.generateAccessToken(user);

        return new AuthResponse(accessToken, user.getId(), user.getEmail(), user.getRole(), user.getCreatedDate());
    }

    @Override
    public UserResponse getMe(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(userId, user.getName(), user.getEmail(), user.getAvatar(), user.getEnabled(),
                user.getLocked(), user.getRole(), user.getCreatedDate(), user.getUpdatedDate());
    }
}