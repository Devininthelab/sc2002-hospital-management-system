package org.example.entity;

public class Medicine {
    private String name;
    private int stockLevel;
    private int lowStockAlert;
    private boolean requested;  // New field for tracking replenishment requests

    public Medicine(String name, int stockLevel, int lowStockAlert) {
        this.name = name;
        this.stockLevel = stockLevel;
        this.lowStockAlert = lowStockAlert;
        this.requested = false;  // Default to false
    }

    // Getter and Setter for requested
    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getStockLevel() {
        return stockLevel;
    }
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }
    public int getLowStockAlert() {
        return lowStockAlert;
    }
    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }
    public String toString() {
        return "Medicine{" +
                "medicineName='" + name + '\'' +
                ", stockLevel=" + stockLevel +
                ", lowStockAlert=" + lowStockAlert +
                ", requested=" + requested +
                '}';
    }
}
