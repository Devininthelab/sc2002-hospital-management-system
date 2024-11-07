package org.example.entity;

public class Staff {
    private String staffId;
    private String password;
    private String role;
    private String name;
    private int age;

    public Staff(String staffId, String password, String role, String name, int age) {
        this.staffId = staffId;
        this.password = password;
        this.role = role;
        this.name = name;
        this.age = age;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void updateProfile(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public String toString() {
        // this part I write for testing
        return "Staff ID: " + staffId + "\tName: " + name + "\tRole: " + role + "\tAge: " + age;
    }
}
