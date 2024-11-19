package org.example.entity;

/**
 * Represents a medicine with details about its name, stock level,
 * and stock thresholds for inventory management.
 */
public class Medicine {
    private String name;
    private int stockLevel;
    private int lowThreshold;
    private int highThreshold;

    /**
     * Constructor to initialize a medicine object.
     *
     * @param name         the name of the medicine
     * @param stockLevel   the current stock level of the medicine
     * @param lowThreshold the stock level below which the medicine is considered low
     * @param highThreshold the stock level above which the medicine is considered overstocked
     */
    public Medicine(String name, int stockLevel, int lowThreshold, int highThreshold) {
        this.name = name;
        this.stockLevel = stockLevel;
        this.lowThreshold = lowThreshold;
        this.highThreshold = highThreshold;
    }


    /**
     * Retrieves the name of the medicine.
     *
     * @return the name of the medicine
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the medicine.
     *
     * @param name the new name of the medicine
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the current stock level of the medicine.
     *
     * @return the stock level of the medicine
     */
    public int getStockLevel() {
        return stockLevel;
    }

    /**
     * Sets the current stock level of the medicine.
     *
     * @param stockLevel the new stock level of the medicine
     */
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    /**
     * Retrieves the low stock threshold for the medicine.
     *
     * @return the low stock threshold
     */
    public int getLowThreshold() {
        return lowThreshold;
    }

    /**
     * Sets the low stock threshold for the medicine.
     *
     * @param lowThreshold the new low stock threshold
     */
    public void setLowThreshold(int lowThreshold) {
        this.lowThreshold = lowThreshold;
    }

    /**
     * Retrieves the high stock threshold for the medicine.
     *
     * @return the high stock threshold
     */
    public int getHighThreshold() {
        return highThreshold;
    }

    /**
     * Sets the high stock threshold for the medicine.
     *
     * @param highThreshold the new high stock threshold
     */
    public void setHighThreshold(int highThreshold) {
        this.highThreshold = highThreshold;
    }

    /**
     * Checks if the stock level is below the low threshold.
     *
     * @return true if the stock level is below the low threshold, false otherwise
     */
    public boolean isLowStock() {
        return stockLevel < lowThreshold;
    }

    /**
     * Returns a string representation of the medicine object.
     *
     * @return a string containing the name, stock level, and thresholds of the medicine
     */
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", stockLevel=" + stockLevel +
                ", lowThreshold=" + lowThreshold +
                ", highThreshold=" + highThreshold +
                '}';
    }
}
