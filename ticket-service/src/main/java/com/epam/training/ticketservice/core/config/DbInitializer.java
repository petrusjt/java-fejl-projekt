package com.epam.training.ticketservice.core.config;

import com.epam.training.ticketservice.core.user.persistence.entity.Role;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DbInitializer {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public DbInitializer(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        if (!userRepository.existsByUsername("admin")) {
            final User user = new User(
                    null,
                    "admin",
                    passwordEncoder.encode("admin"),
                    Role.ADMIN);
            userRepository.save(user);
        }
    }
}
