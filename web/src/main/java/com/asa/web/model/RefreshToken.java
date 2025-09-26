package com.asa.web.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Entity @Table(name = "refresh_tokens", indexes = {
@Index(name = "idx_rt_token", columnList = "token", unique = true),
@Index(name = "idx_rt_user", columnList = "user_id")
})
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class RefreshToken {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 200)
    private String token; // cryptographically random string (not JWT)

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AppUser user;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean revoked;

    private Instant revokedAt;
    private String ip;
    private String userAgent;
    private String replacedBy; // for rotation tracking
}
