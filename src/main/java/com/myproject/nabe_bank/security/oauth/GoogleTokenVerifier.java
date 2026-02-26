package com.myproject.nabe_bank.security.oauth;

public interface GoogleTokenVerifier {
    GooglePayload verify(String idToken);
}
