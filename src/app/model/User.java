package app.model;

public class User {

    private int id;
    private String names;
    private String surnames;
    private String email;
    private String password;
    private String username;
    private String gender;
    private String token;

    public User(int id, String names, String surnames, String email, String password, String username, String gender) {
        this.names = names;
        this.surnames = surnames;
        this.email = email;
        this.password = password;
        this.username = username;
        this.gender = gender;
        this.token = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
