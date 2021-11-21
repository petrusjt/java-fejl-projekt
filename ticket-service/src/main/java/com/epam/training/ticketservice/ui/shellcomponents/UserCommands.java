package com.epam.training.ticketservice.ui.shellcomponents;

import com.epam.training.ticketservice.core.LoginService;
import com.epam.training.ticketservice.core.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class UserCommands {

    private final LoginService loginService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserCommands(LoginService loginService, PasswordEncoder passwordEncoder, UserService userService) {
        this.loginService = loginService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @ShellMethod(value = "Sign in privileged", key = "sign in privileged")
    public void signInPrivileged(String username, String password) {
        loginService.signIn(username, password);
    }

    @ShellMethod(value = "Sign out", key = "sign out")
    //@ShellMethodAvailability("isUserSignedIn")
    public void signOut() {
        loginService.signOut();
    }

    @ShellMethod(value = "Describe account", key = "describe account")
    public String describeAccount() {
        try {
            final UserDto userDto = userService.describeAccount();
            if (userDto.getRole().equals(Role.ADMIN)) {
                return "Signed in with privileged account '"
                        + userDto.getUsername()
                        + "'";
            } else {
                //TODO
                return "";
            }
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}
