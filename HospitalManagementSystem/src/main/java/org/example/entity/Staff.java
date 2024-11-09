package org.example.entity;

public class Staff {
    private String id;
    private String name;
    private String role;
    private String password;
    private String gender;
    private int age;


    public Staff(String id, String name, String role, String gender, int age, String password) {
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

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return this.gender;
    }
    public int getAge() { return age; }

    public void setId(String id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setRole(String role) { this.role = role; }

    public void setPassword(String password) { this.password = password; }

    public void setGender(String gender){
        if(gender.toLowerCase() == "male"){
            this.gender = "Male";
        }
        else if(gender.toLowerCase() == "female"){
            this.gender = "Female";
        }
        else{
            System.out.println("Gender is not valid! Please try again.\n");
        }
    }

    public void setAge(int age) { this.age = age; }
}
