package com.dorovidal.medical_system.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class RoleAuthController {

    public boolean hasPermission(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User userDetails)) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            return false;
        }

        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(authority)) {
                return true;
            }
        }

        return false;
    }
}
