package com.epam.training.ticketservice.core.user.impl;

import com.epam.training.ticketservice.core.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void signIn(final String username, final String password) {
        try {
            final Authentication result = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(result);
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Login failed due to incorrect credentials") {
            };
        }
    }

    @Override
    public void signOut() {
        SecurityContextHolder.clearContext();
    }
}
