package com.example.bankcards.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CurrentUserProvider {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserDetails getUserDetails() {
        Authentication auth = getAuthentication();
        if (Objects.nonNull(auth) && auth.getPrincipal() instanceof UserDetails) {
            return (UserDetails) auth.getPrincipal();
        }
        return null;
    }

    public String getUsername() {
        UserDetails userDetails = getUserDetails();
        return (Objects.nonNull(userDetails)) ? userDetails.getUsername() : null;
    }

    public Long getUserId() {
        UserDetails userDetails = getUserDetails();
        if (userDetails instanceof CardUserDetails cardUser) {
            return cardUser.getId();
        }
        return null;
    }
}
