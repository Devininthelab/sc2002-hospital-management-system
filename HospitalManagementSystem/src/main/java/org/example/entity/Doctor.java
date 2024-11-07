package org.example.entity;

public class Doctor extends Staff {
    private String[][] schedule;

    public Doctor(String id, String name, String role, String password, Patient.Gender gender, int age) {
        super(id, name, role, gender, age, password);
        schedule = new String[20][7];
    }

    public String[][] getSchedule() {
        return schedule;
    }

    public void setSchedule(String[][] schedule) {
        this.schedule = schedule;
    }

}
