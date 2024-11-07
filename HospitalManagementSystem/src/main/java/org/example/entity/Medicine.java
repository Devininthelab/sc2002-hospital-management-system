package org.example.entity;

public class Medicine {
    private String name;
    private int stockLevel;
    private int lowStockAlert;

    public Medicine(String name, int stockLevel, int lowStockAlert) {
        this.name = name;
        this.stockLevel = stockLevel;
        this.lowStockAlert = lowStockAlert;
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
        return "Medication{" +
                "name='" + name + '\'' +
                ", stockLevel=" + stockLevel +
                ", lowStockAlert=" + lowStockAlert +
                '}';
    }
}
