package com.myproject.nabe_bank.core.security.oauth;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service
public class GoogleTokenVerifierImpl implements GoogleTokenVerifier {
    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifierImpl(
            @Value("${google.client-id}") String clientId) {

        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    @Override
    public GooglePayload verify(String idToken) {

        try {
            GoogleIdToken verifiedToken = verifier.verify(idToken);

            if (verifiedToken == null) {
                throw new RuntimeException("Invalid Google token");
            }

            GoogleIdToken.Payload payload = verifiedToken.getPayload();

            return new GooglePayload(
                    payload.getSubject(),
                    payload.getEmail(),
                    (String) payload.get("name"),
                    (String) payload.get("picture"),
                    payload.getEmailVerified());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
