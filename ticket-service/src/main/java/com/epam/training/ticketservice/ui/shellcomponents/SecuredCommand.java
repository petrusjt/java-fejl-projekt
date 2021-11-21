package com.epam.training.ticketservice.ui.shellcomponents;

import com.epam.training.ticketservice.core.user.persistence.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;

import java.util.Optional;

public abstract class SecuredCommand {

    public Availability isUserLoggedIn() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<? extends GrantedAuthority> role = auth.getAuthorities().stream().findFirst();

        if (role.isPresent()) {
            return Availability.available();
        }
        return Availability.unavailable("You are not signed in.");
    }

    public Availability isAdminUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<? extends GrantedAuthority> role = auth.getAuthorities().stream().findFirst();

        if (role.isPresent() && role.get().equals(Role.ADMIN)) {
            return Availability.available();
        }
        return Availability.unavailable("This command requires privileged account.");
    }


}
