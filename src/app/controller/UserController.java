package app.controller;

import app.dto.UserResponseDTO;
import app.model.User;
import app.service.UserService;

public class UserController {

    UserService userService;
    public UserController() {
        userService = new UserService();
    }

    public UserResponseDTO saveUser(String names, String surnames, String email, String password, String username, String gender) {
        return this.userService.saveUser(names, surnames, email, password, username, gender);
    }

    public UserResponseDTO updateUser(int id, User user, UserResponseDTO userRDTO) {
        return this.userService.updateUser(id, user, userRDTO);
    }

    public void deleteUser(int id, UserResponseDTO user) {
        this.userService.deleteUser(id, user);
    }

    public UserResponseDTO loadUser(String username, String password) {
        return this.userService.loadUser(username, password);
    }
}
