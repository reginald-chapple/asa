package com.asa.web.jwt;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asa.web.model.AppUser;
import com.asa.web.model.RefreshToken;
import com.asa.web.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository repo;
    private final long refreshDays;
    private final SecureRandom random = new SecureRandom();

    public RefreshTokenService(RefreshTokenRepository repo,
            @Value("${app.jwt.refresh-days}") long refreshDays) {
        this.repo = repo;
        this.refreshDays = refreshDays;
    }

    public RefreshToken mint(AppUser user, String ip, String ua) {
        String token = randomToken();
        var rt = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiresAt(Instant.now().plusSeconds(refreshDays * 86400))
                .revoked(false)
                .ip(ip).userAgent(ua)
                .build();
        return repo.save(rt);
    }

    public RefreshToken rotate(RefreshToken current, AppUser user, String ip, String ua) {
        current.setRevoked(true);
        current.setRevokedAt(Instant.now());
        var next = mint(user, ip, ua);
        current.setReplacedBy(next.getToken());
        repo.save(current);
        return next;
    }

    public RefreshToken requireActive(String token) {
        var rt = repo.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        if (rt.isRevoked() || rt.getExpiresAt().isBefore(Instant.now()))
            throw new IllegalStateException("Refresh token expired or revoked");
        return rt;
    }

    public void revokeAllForUser(UUID userId) {
        repo.findAllByUserIdAndRevokedFalse(userId).forEach(rt -> {
            rt.setRevoked(true);
            rt.setRevokedAt(Instant.now());
        });
        repo.flush();
    }

    private String randomToken() {
        byte[] buf = new byte[64];
        random.nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }
}
