package org.example.entity;

/**
 * Represents a staff member in the system.
 * <p>
 * Staff members have attributes such as ID, name, role, password, gender, and age.
 * </p>
 */
public class Staff {
    private String id;
    private String name;
    private String role;
    private String password;
    private String gender;
    private int age;

    /**
     * Constructor to create a new Staff object.
     *
     * @param id       the unique identifier for the staff member
     * @param name     the name of the staff member
     * @param role     the role of the staff member (e.g., "Doctor", "Pharmacist")
     * @param gender   the gender of the staff member
     * @param age      the age of the staff member
     * @param password the password for the staff member's account
     */
    public Staff(String id, String name, String role, String gender, int age, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
        this.gender = gender;
        this.age = age;
    }

    /**
     * Retrieves the ID of the staff member.
     *
     * @return the staff member's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the name of the staff member.
     *
     * @return the staff member's name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the role of the staff member.
     *
     * @return the staff member's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Retrieves the password of the staff member.
     *
     * @return the staff member's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the gender of the staff member.
     *
     * @return the staff member's gender
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Retrieves the age of the staff member.
     *
     * @return the staff member's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Updates the ID of the staff member.
     *
     * @param id the new ID for the staff member
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Updates the name of the staff member.
     *
     * @param name the new name for the staff member
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the role of the staff member.
     *
     * @param role the new role for the staff member
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Updates the password of the staff member.
     *
     * @param password the new password for the staff member
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Updates the gender of the staff member.
     * <p>
     * Validates the input to ensure it is either "Male" or "Female".
     * If the input is invalid, an error message is displayed.
     * </p>
     *
     * @param gender the new gender for the staff member
     */
    public void setGender(String gender) {
        if (gender.toLowerCase() == "male") {
            this.gender = "Male";
        } else if (gender.toLowerCase() == "female") {
            this.gender = "Female";
        } else {
            System.out.println("Gender is not valid! Please try again.\n");
        }
    }

    /**
     * Updates the age of the staff member.
     *
     * @param age the new age for the staff member
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns a string representation of the staff member.
     *
     * @return a string containing the staff member's ID, name, role, gender, and age
     */
    public String toString() {
        return "ID: " + id + "\tName: " + name + "\tRole: " + role + "\tGender:" + gender + "\tAge: " + age;
    }
}
