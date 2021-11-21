package com.epam.training.ticketservice.core.user.impl;

import com.epam.training.ticketservice.core.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDto describeAccount() {
        try {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<? extends GrantedAuthority> role = auth.getAuthorities().stream().findFirst();
            return UserDto.builder()
                    .username(auth.getName())
                    .role((Role) role.orElseThrow(IllegalStateException::new))
                    .build();
        } catch (Exception e) {
            throw new IllegalStateException("You are not signed in");
        }
    }
}
