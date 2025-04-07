package app.model;

import java.util.UUID;

public class User {

    private String id;
    private String names;
    private String surnames;
    private String email;
    private String password;
    private String username;
    private String gender;

    public User(String names, String surnames, String email, String password, String username, String gender) {
        this.id = UUID.randomUUID().toString();
        this.names = names;
        this.surnames = surnames;
        this.email = email;
        this.password = password;
        this.username = username;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
