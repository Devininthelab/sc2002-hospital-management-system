package org.example.entity;

public class Medicine {
    private String name;
    private int stockLevel;
    private int lowThreshold;
    private int highThreshold;

    public Medicine(String name, int stockLevel, int lowThreshold, int highThreshold) {
        this.name = name;
        this.stockLevel = stockLevel;
        this.lowThreshold = lowThreshold;
        this.highThreshold = highThreshold;
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
    public int getLowThreshold() {
        return lowThreshold;
    }

    public void setLowThreshold(int lowThreshold) {
        this.lowThreshold = lowThreshold;
    }

    public int getHighThreshold() {
        return highThreshold;
    }

    public void setHighThreshold(int highThreshold) {
        this.highThreshold = highThreshold;
    }

    public boolean isLowStock() {
        return stockLevel < lowThreshold;
    }

    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", stockLevel=" + stockLevel +
                ", lowThreshold=" + lowThreshold +
                ", highThreshold=" + highThreshold +
                '}';
    }
}
