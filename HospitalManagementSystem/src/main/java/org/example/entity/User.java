package org.example.entity;

public class User {
    public String username;
    public String password;
    public String role;
    public String contact;
    public int id;

    public User(String username, String password, String role, String contact) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.contact = contact;
    }

    //getter and setter methods for each attribute
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Method to login
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    //Method to change the password
    public void changePassword(String password) {
        this.password = password;
    }

    //Method to view the profile
    public String viewProfile() {
        return "Username: " + this.username + "\nRole: " + this.role + "\nContact: " + this.contact;
    }

    //Method to update the profile
    public void updateProfile(String username, String contact) {
        this.username = username;
        this.contact = contact;
    }
}


