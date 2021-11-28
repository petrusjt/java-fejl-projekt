package com.epam.training.ticketservice.core.user.impl;

import com.epam.training.ticketservice.core.security.LoginService;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final LoginService loginService;

    public UserServiceImpl(final LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public UserDto describeAccount() {
        try {
            final UserDto user = loginService.getLoggedInUserData();
            if (user != null) {
                return UserDto.builder()
                        .username(user.getUsername())
                        .role(user.getRole())
                        .build();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new IllegalStateException("You are not signed in");
        }
    }
}
