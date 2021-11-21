package com.epam.training.ticketservice.core;

public interface LoginService {

    void signIn(final String username, final String password);

    void signOut();
}
