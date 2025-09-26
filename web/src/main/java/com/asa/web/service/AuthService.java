package com.asa.web.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asa.web.dto.*;
import com.asa.web.model.AppUser;
import com.asa.web.jwt.JwtService;
import com.asa.web.jwt.RefreshTokenService;
import com.asa.web.repository.UserRepository;


@Service
public class AuthService {
    
    private final UserRepository users;
    private final RefreshTokenService refreshTokens;
    private final JwtService jwt;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository users, RefreshTokenService refreshTokens, JwtService jwt,
        PasswordEncoder encoder, AuthenticationManager authManager) {
        this.users = users; this.refreshTokens = refreshTokens; this.jwt = jwt;
        this.encoder = encoder; this.authManager = authManager;
    }

    public AuthTokensResponse register(RegistrationRequest req) {
        if (users.existsByEmail(req.getEmail())) throw new IllegalArgumentException("Email already registered");
        var user = users.save(AppUser.builder()
            .email(req.getEmail())
            .firstName(req.getFirstName())
            .lastName(req.getLastName())
            .password(encoder.encode(req.getPassword()))
            .build());
            return issueTokens(user);
    }

    public AuthTokensResponse login(LoginRequest req) {
        var auth = new UsernamePasswordAuthenticationToken(req.email(), req.password());
        authManager.authenticate(auth); // throws on failure
        var user = users.findByEmail(req.email()).orElseThrow();
        return issueTokens(user);
    }

    public AuthTokensResponse refresh(String refreshToken) {
        var current = refreshTokens.requireActive(nonEmpty(refreshToken));
        var user = current.getUser();
        var next = refreshTokens.rotate(current, user, null, null);
        var access = jwt.generateAccessToken(user);
        return new AuthTokensResponse(access, next.getToken(), "Bearer");
    }

    /**
    * Logout semantics: if a refresh token is provided, revoke that one; otherwise revoke all for the user
    * identified by the provided (already-validated) access principal.
    */
    public void logout(String refreshToken, String principalEmail) {
        if (refreshToken != null && !refreshToken.isBlank()) {
            var rt = refreshTokens.requireActive(refreshToken);
            rt.setRevoked(true); rt.setRevokedAt(java.time.Instant.now());
            refreshTokens.rotate(rt, rt.getUser(), null, null); // optional: mark as replaced to close chain
            return;
        }
        if (principalEmail != null) {
            var user = users.findByEmail(principalEmail).orElse(null);
            if (user != null) refreshTokens.revokeAllForUser(user.getId());
        }
    }

    private AuthTokensResponse issueTokens(AppUser user) {
        var access = jwt.generateAccessToken(user);
        var rt = refreshTokens.mint(user, null, null);
        return new AuthTokensResponse(access, rt.getToken(), "Bearer");
    }

    private String nonEmpty(String v) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException("Refresh token required");
        return v;
    }
}