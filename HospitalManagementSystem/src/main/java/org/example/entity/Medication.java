package org.example.entity;

public class Medication {
    public enum Status {
        PENDING, DISPENSED;
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
                ", status=" + status +
                '}';
    }
}