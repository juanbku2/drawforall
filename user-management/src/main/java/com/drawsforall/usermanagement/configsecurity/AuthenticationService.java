package com.drawsforall.usermanagement.configsecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthenticationService implements AuthenticationServiceInterface {

    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isAnonymousAuthentication() {
        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals(ROLE_ANONYMOUS));
    }
}