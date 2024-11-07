package org.example.entity;

public class Staff {
    private String id;
    private String name;
    private String role;
    private String password;
    private Patient.Gender gender;
    private int age;

    public Staff(String id, String name, String role, Patient.Gender gender, int age, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
        this.gender = gender;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
