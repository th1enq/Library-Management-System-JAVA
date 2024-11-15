package src.librarysystem;

import javafx.beans.value.ObservableValue;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String userType;
    private boolean isBanned;
    private String avatarLink;

    public User() {
    }

    public User(int id, String name, String username, String password, String userType, boolean isBanned, String avatarLink) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.isBanned = isBanned;
        this.avatarLink = avatarLink;
    }

    public User(int id, String name, String username, String password, String userType) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.isBanned = false;
        this.avatarLink = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", name=" + name + ' ' +
                        ", username=" + username + ' ' +
                        ", userType=" + userType + ' ' +
                        ", isBanned=" + isBanned +
                        ", avatarLink=" + avatarLink + ' '
                ;
    }

    public ObservableValue<String> getStatus() {
        return null;
    }
}