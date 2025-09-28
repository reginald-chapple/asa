package com.asa.web.service;

import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.asa.web.model.AppUser;

@Component
public class CurrentUserService {

    public UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof AppUser appUser) {
            return appUser.getId(); // AppUser has UUID id
        }

        throw new IllegalStateException("No authenticated user found");
    }

    public AppUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof AppUser appUser) {
            return appUser;
        }

        throw new IllegalStateException("No authenticated user found");
    }
}

