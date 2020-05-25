package com.demo.service;

import com.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository users;

    public UserServiceImpl() {
    }

    @Override
    public Iterable<User> findUsers() {
        return users.findAll();
    }

    @Override
    public void createUser(User user) {
        users.save(user);
    }

}


