package org.example.entity;

public class Medication {
    private final String medicineName;
    private int stockLevel;
    private int lowStockAlert;
    private boolean requested;

    public Medication(String medicineName, int stockLevel, int lowStockAlert) {
        this.medicineName = medicineName;
        this.stockLevel = stockLevel;
        this.lowStockAlert = lowStockAlert;
        this.requested = false;
    }
    //Getters and Setters
    public String getName() {
        return medicineName;
    }

    public int getStockLevel() {
        return stockLevel;
    }
    public int getLowStockAlert() {
        return lowStockAlert;
    }
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }
    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }



    @Override
    public String toString() {
        return "Medication{" +
                "medicineName='" + medicineName + '\'' +
                ", stockLevel=" + stockLevel +
                ", lowStockAlert=" + lowStockAlert +
                ", requested=" + requested +
                '}';
    }
}
