package org.example.src;
public class User {
    private String username;
    private String password;
    private String type;
    private String email;

    public User(String username, String password, String type, String email) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;
        return username.equals(user.getUsername()) && password.equals(user.getPassword());
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Password: " + password + ", Email: " + email;
    }
}