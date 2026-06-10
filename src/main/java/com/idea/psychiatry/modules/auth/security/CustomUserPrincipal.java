package com.idea.psychiatry.modules.auth.security;

import com.idea.psychiatry.modules.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


public class CustomUserPrincipal implements UserDetails {

    // ── Getters (برای JWT claims) ─────────────
    @Getter
    private final UUID userId;
    @Getter
    private final UUID organizationId;
    private final String email;
    private final String passwordHash;
    @Getter
    private final String role;
    private final boolean active;

    public CustomUserPrincipal(User user) {
        this.userId         = user.getId();
        this.organizationId = user.getOrganizationId();
        this.email          = user.getEmail();
        this.passwordHash   = user.getPasswordHash();
        this.role           = user.getRole().name();
        this.active         = user.isActive();
    }

    // ── UserDetails ───────────────────────────
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override public String getPassword()   { return passwordHash; }
    @Override public String getUsername()   { return email; }
    @Override public boolean isEnabled()    { return active; }



}