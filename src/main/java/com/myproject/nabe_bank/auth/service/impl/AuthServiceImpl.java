package com.myproject.nabe_bank.auth.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.myproject.nabe_bank.auth.dto.request.GoogleSignInRequest;
import com.myproject.nabe_bank.auth.dto.response.AuthResponse;
import com.myproject.nabe_bank.auth.service.AuthService;
import com.myproject.nabe_bank.security.jwt.JwtProvider;
import com.myproject.nabe_bank.security.oauth.GooglePayload;
import com.myproject.nabe_bank.security.oauth.GoogleTokenVerifier;
import com.myproject.nabe_bank.user.User;
import com.myproject.nabe_bank.user.UserRepository;
import com.myproject.nabe_bank.user.UserRole;

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
}