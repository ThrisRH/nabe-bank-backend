package com.myproject.nabe_bank.core.security.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.myproject.nabe_bank.modules.user.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
    private final String secretKey = "g1vBqcFWJEeksBHd4oCxt3ZKSwAesvbj8jY3LPqQXAY3LgJaEKWPGfUWLWwlCRSy";
    private final long accessTokenExpiration = 1000 * 60 * 15;

    public String generateAccessToken(User user) {
        return Jwts.builder().setSubject(user.getId().toString()).claim("role", user.getRole().name())
                .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
