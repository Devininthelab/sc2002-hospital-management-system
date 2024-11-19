package org.example.entity;

/**
 * Medication is the prescription requested in the AppointmentOutcomeRecord.
 * <p>
 * It contains:
 * <ul>
 *     <li>An ID pointing to the respective outcome record ID.</li>
 *     <li>A name for the medication.</li>
 *     <li>A quantity requested.</li>
 *     <li>A status for the request, such as PENDING, APPROVED, or REJECTED.</li>
 * </ul>
 * </p>
 */
public class Prescription {

    /**
     * ID is the same as the appointment ID and the appointment outcome record ID.
     */
    private int id;
    private String name;
    private int quantity;
    /**
     * Status can be PENDING, APPROVED, or REJECTED.
     */
    private String status;

    /**
     * Constructor for creating a Prescription from the database's perspective.
     *
     * @param id       the ID of the prescription, corresponding to the appointment and outcome record
     * @param name     the name of the medication
     * @param quantity the quantity requested for the prescription
     * @param status   the status of the prescription (e.g., PENDING, APPROVED, REJECTED)
     */
    public Prescription(int id, String name, int quantity, String status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * Constructor for adding a prescription from the doctor's perspective.
     * <p>
     * Initializes the prescription with a default status of "PENDING".
     * </p>
     *
     * @param id       the ID of the prescription, corresponding to the appointment and outcome record
     * @param name     the name of the medication
     * @param quantity the quantity requested for the prescription
     */
    public Prescription(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.status = "PENDING";
    }

    /**
     * Retrieves the ID of the prescription.
     *
     * @return the prescription ID
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of the medication.
     *
     * @return the medication name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the current status of the prescription.
     *
     * @return the prescription status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Retrieves the quantity requested for the prescription.
     *
     * @return the quantity of the prescription
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Updates the status of the prescription.
     *
     * @param status the new status of the prescription (e.g., PENDING, APPROVED, REJECTED)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Updates the quantity requested for the prescription.
     *
     * @param quantity the new quantity for the prescription
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns a string representation of the prescription.
     *
     * @return a string containing the prescription details, including ID, name, quantity, and status
     */
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
