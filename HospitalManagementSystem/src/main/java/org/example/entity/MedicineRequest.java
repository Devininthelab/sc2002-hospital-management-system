package org.example.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a request for medicines, including the request ID,
 * a list of medicines, and the current status of the request.
 */
public class MedicineRequest {
    private int id;
    private List<String> medicines;
    private String status;

    /**
     * Constructor to initialize a new MedicineRequest object.
     *
     * @param id        the unique identifier for the medicine request
     * @param status    the current status of the request (e.g., "Pending", "Approved")
     * @param medicines a list of medicines included in the request
     */
    public MedicineRequest(int id, String status, List<String> medicines) {
        this.id = id;
        this.status = status;
        this.medicines = medicines;
    }

    /**
     * Retrieves the list of medicines in the request.
     *
     * @return a list of medicine names
     */
    public List<String> getMedicines() {
        return medicines;
    }

    /**
     * Retrieves the unique identifier of the medicine request.
     *
     * @return the ID of the request
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the current status of the request.
     *
     * @return the current status of the request
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the medicine request.
     * Converts the status to uppercase before storing it.
     *
     * @param status the new status of the request
     */
    public void setStatus(String status) {
        this.status = status.toUpperCase();
    }

    /**
     * Sets the unique identifier for the medicine request.
     *
     * @param id the new ID for the request
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the list of medicines for the request.
     *
     * @param medicines the new list of medicines to include in the request
     */
    public void setMedicines(List<String> medicines) {
        this.medicines = medicines;
    }



    /**
     * Returns a string representation of the medicine request.
     *
     * @return a string containing the request ID, list of medicines, and status
     */
    @Override
    public String toString() {
        return "MedicineRequest{" +
                "id=" + id +
                ", medicines=" + medicines +
                ", status='" + status + '\'' +
                '}';
    }
}
