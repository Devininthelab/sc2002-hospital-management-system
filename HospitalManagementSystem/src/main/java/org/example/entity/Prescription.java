package org.example.entity;

/**
 * Medication is the prescription requested in the AppointmentOutcomeRecord
 * It contains an id pointing to respective outcome record id
 * a name for the medication
 * a quantity requested
 * and a status for request
 */
public class Prescription {

    /**
     * id is the same as appointment id and appointment outcome record id
     */
    private int id;
    private String name;
    private int quantity;
    /**
     * status can be PENDING, APPROVED, REJECTED
     */
    private String status;

    // Database's Perspective
    public Prescription(int id, String name, int quantity, String status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }
    // Doctor's Adding
    public Prescription(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.status = "PENDING";
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}