package com.epam.training.ticketservice.core.user.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class UserLoginDto {

    private final String username;
    private final String password;
}
