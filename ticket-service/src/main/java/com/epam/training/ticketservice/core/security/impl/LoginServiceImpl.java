package com.epam.training.ticketservice.core.security.impl;

import com.epam.training.ticketservice.core.security.LoginService;
import com.epam.training.ticketservice.core.security.exception.AuthenticationException;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.model.UserLoginDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private UserDto loggedInUserData;

    public LoginServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signIn(final UserLoginDto userLoginData) throws AuthenticationException {
        final Optional<User> user = userRepository.findByUsername(userLoginData.getUsername());
        if (user.isPresent()) {
            if (passwordEncoder.matches(userLoginData.getPassword(), user.get().getPassword())) {
                loggedInUserData = new UserDto(user.get().getUsername(), user.get().getRole());
            } else {
                throw new AuthenticationException("Login failed due to incorrect credentials");
            }
        } else {
            throw new AuthenticationException("Login failed due to incorrect credentials");
        }
    }

    @Override
    public void signOut() {
        loggedInUserData = null;
    }

    @Override
    public UserDto getLoggedInUserData() {
        return loggedInUserData;
    }
}
