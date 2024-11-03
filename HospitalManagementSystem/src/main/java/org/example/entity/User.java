package org.example.entity;

public class User {
    public int id;
    public String password;
    public String role;

    public User(int id, String password, String role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }

    //getter and setter methods for each attribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


