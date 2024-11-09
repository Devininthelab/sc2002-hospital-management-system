package org.example.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicineRequest {
    private int id;
    private List<String> medicines;
    private String status;

    public MedicineRequest(int id, String status, List<String> medicines) {
        this.id = id;
        this.status = status;
        this.medicines = medicines;
    }

    public List<String> getMedicines() {
        return medicines;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}