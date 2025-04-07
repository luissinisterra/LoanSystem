package app.controller;

import app.model.User;
import app.service.UserService;

public class UserController {

    UserService userService;
    public UserController() {
        userService = new UserService();
    }

    public User saveUser(String names, String surnames, String email, String password, String username, String gender) {
        return this.userService.saveUser(names, surnames, email, password, username, gender);
    }
}
