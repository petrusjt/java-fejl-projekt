package com.epam.training.ticketservice.core.security;

import com.epam.training.ticketservice.core.security.exception.AuthenticationException;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.model.UserLoginDto;

public interface LoginService {

    void signIn(final UserLoginDto userLoginData) throws AuthenticationException;

    void signOut();

    UserDto getLoggedInUserData();
}
