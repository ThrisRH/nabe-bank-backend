package com.myproject.nabe_bank.core.security.oauth;

public interface GoogleTokenVerifier {
    GooglePayload verify(String idToken);
}
