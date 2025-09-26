package com.asa.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, unique = true, columnDefinition = "uuid")
    private UUID id;

    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    @Column(length = 150, nullable = false)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(length = 150, nullable = false)
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Pattern(regexp = "^1?\\d{9,15}$", message = "Phone number must be in the format '123456789'. Up to 15 digits allowed.")
    @Length(max = 16)
    private String phoneNumber;

    private LocalDate birthdate;

    @Length(max = 50)
    private String address;

    @Length(max = 50)
    private String city;

    @Length(max = 50)
    private String country;

    @Length(max = 50)
    private String state;

    @Length(max = 12)
    private String zip;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Role role = Role.ROLE_USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getAuthority()));
    }

    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
