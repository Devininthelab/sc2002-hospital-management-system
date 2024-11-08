package org.example.entity;

import org.example.utils.Gender;

public class Staff {
    private String id;
    private String name;
    private String role;
    private String password;
    private Gender gender;
    private int age;
    private String contact;

    public Staff(String id, String name, String role, Gender gender, int age, String password, String contact) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getContact() { return contact; }

    public String getGender() {
        if(gender == Gender.MALE){ return "Male"; }
        else{ return "Female"; }
    }
    public int getAge() { return age; }

    public void setId(String id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setRole(String role) { this.role = role; }

    public void setPassword(String password) { this.password = password; }

    public void setContact(String contact) { this.contact = contact; }

}
