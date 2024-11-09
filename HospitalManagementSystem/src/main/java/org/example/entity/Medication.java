package org.example.entity;

public class Medication {
    public enum Status {
        PENDING, DISPENSED;
    }

    private int id;
    private String name;
    private int quantity;
    private Status status;

    // Database's Perspective
    public Medication(int id, String name, int quantity, Status status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }
    // Doctor's Adding
    public Medication(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.status = Status.PENDING;
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
    public int getQuantity() {
        return quantity;
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