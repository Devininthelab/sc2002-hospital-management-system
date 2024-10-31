package org.example.control;

import org.example.entity.User;
import org.example.entity.UserRepository;

import java.util.List;

public class AccountService {
    private UserRepository userRepository;

    public AccountService() {
        this.userRepository = new UserRepository();
    }

    public User login(String username, String password) {
        // get list of users
        List<User> users = this.userRepository.getUsers();
        // match username and password, retrieve user
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
