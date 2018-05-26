package system.attendance.electronic.model;

import system.attendance.electronic.common.SnowFlakeUtil;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;

    private Byte root = 0;

    private String username;

    private String password;

    public User() {
        id = SnowFlakeUtil.get();
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getRoot() {
        return root;
    }

    public void setRoot(Byte root) {
        this.root = root;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", root=" + root +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}