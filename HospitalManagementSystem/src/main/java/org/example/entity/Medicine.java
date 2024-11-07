package org.example.entity;

public class Medicine {
    private String medicineName;
    private MedicineDispenseStatus status;


    public Medicine(String medicineName, MedicineDispenseStatus status) {
        this.medicineName = medicineName;
        this.status = MedicineDispenseStatus.PENDING;
    }


    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public MedicineDispenseStatus getStatus() {
        return status;
    }

    public void setStatus(MedicineDispenseStatus status) {
        this.status = status;
    }

    public void dispenseMedicine() {
        this.status = MedicineDispenseStatus.DISPENSED;
    }

    public void pendingMedicine() {
        this.status = MedicineDispenseStatus.PENDING;
    }


    public String toString() {
        return "Medicine Name: " + medicineName + "\n" + "Status: " + status;
    }
}
