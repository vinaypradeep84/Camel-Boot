package com.demo.service;


import com.demo.model.User;

/**
 * Service interface for managing users.
 *
 */
public interface UserService {

    Iterable<User> findUsers();

    void createUser(User user);
}