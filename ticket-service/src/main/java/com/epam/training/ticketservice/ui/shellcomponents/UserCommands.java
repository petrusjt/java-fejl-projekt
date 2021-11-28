package com.epam.training.ticketservice.ui.shellcomponents;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.security.LoginService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.model.UserLoginDto;
import com.epam.training.ticketservice.core.user.persistence.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class UserCommands extends SecuredCommand{

    private final LoginService loginService;
    private final UserService userService;

    @Autowired
    public UserCommands(LoginService loginService, UserService userService) {
        super(loginService);
        this.loginService = loginService;
        this.userService = userService;
    }

    @ShellMethod(value = "Sign in privileged", key = "sign in privileged")
    public void signInPrivileged(String username, String password) {
        loginService.signIn(new UserLoginDto(username, password));
    }

    @ShellMethod(value = "Sign out", key = "sign out")
    @ShellMethodAvailability("isUserLoggedIn")
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
