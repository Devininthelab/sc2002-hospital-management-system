package org.example.entity;

/**
 * Medication is the prescription requested in the AppointmentOutcomeRecord
 * It contains an id pointing to respective outcome record id
 * a name for the medication
 * a quantity requested
 * and a status for request
 */
public class Medication {
    public enum Status {
        PENDING, DISPENSED;
    }

    /**
     * Convert user input/external service passed parameter
     * to local enum
     * @param status
     * @return corresponding enum
     */
    public static Status stringToStatus(String status) {
        switch (status) {
            case "PENDING":
                return Status.PENDING;
            case "DISPENSED":
                return Status.DISPENSED;
            default:
                return Status.PENDING;
        }
    }

    private int id;
    private String name;
    private int quantity;
    private Status status;

    public Medication(int id, String name, int quantity, Status status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}