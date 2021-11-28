package com.epam.training.ticketservice.ui.shellcomponents;

import com.epam.training.ticketservice.core.security.LoginService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;

import java.util.Optional;

public abstract class SecuredCommand {

    protected final LoginService loginService;

    protected SecuredCommand(LoginService loginService) {
        this.loginService = loginService;
    }

    public Availability isUserLoggedIn() {
        final UserDto user = loginService.getLoggedInUserData();
        if (user != null) {
            if (user.getRole() != null) {
                return Availability.available();
            }
        }
        return Availability.unavailable("You are not signed in.");
    }

    public Availability isAdminUser() {
        final UserDto user = loginService.getLoggedInUserData();
        if (user != null) {
            if (user.getRole() == Role.ADMIN) {
                return Availability.available();
            }
        }
        return Availability.unavailable("This command requires privileged account.");
    }


}
