package org.example.repository;

import org.example.entity.Medicine;
import org.example.entity.MedicineRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Medicine Request Repository
 * <p>Handles the CRUD operations of Medicine Requests</p>
 */
public class MedicineRequestRepository {
    /**
     * counter pointing to the highest current id
     */
    private static int counter;
    private List<MedicineRequest> medicineRequests;
    private String csvPath;

    public MedicineRequestRepository(String filePath) {
        this.csvPath = filePath;
        medicineRequests = new ArrayList<>();
        loadRequestsFromCsv();
        counter = highestId();
    }

    /**
     * Get the highest ID among all medicine requests.
     *
     * @return int The highest ID found in the medicine requests.
     */
    public int highestId() {
        int highest = 0;
        for (MedicineRequest medicineRequest : medicineRequests) {
            if (medicineRequest.getId() > highest) {
                highest = medicineRequest.getId();
            }
        }
        return highest;
    }

    /**
     * Load medicine requests from a CSV file.
     *
     * Reads the CSV file, parses each line, and creates MedicineRequest objects to store in the repository.
     */
    public void loadRequestsFromCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            String header = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                String[] medicineNames = data[1].trim().split(";");
                String status = data[2].trim().toUpperCase();
                medicineRequests.add(new MedicineRequest(id, status, Arrays.asList(medicineNames)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save all medicine requests to a CSV file.
     *
     * Iterates over the list of medicine requests and writes each to the CSV file.
     */
    public void saveRequestsToCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {
            bw.write("id,medicines,status");
            bw.newLine();
            for (MedicineRequest medicineRequest : medicineRequests) {
                String medicineRequestString = String.join(";", medicineRequest.getMedicines());
                bw.write(medicineRequest.getId() + "," + medicineRequestString
                        + "," + medicineRequest.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new medicine request to the repository.
     *
     * @param medicineNames The list of medicine names requested.
     */
    public void addMedicineRequest(List<String> medicineNames) {
        MedicineRequest medicineRequest = new MedicineRequest(counter++, "SUBMITTED", medicineNames);
        medicineRequests.add(medicineRequest);
        saveRequestsToCsv();
    }

    /**
     * Get all medicine requests from the repository.
     *
     * @return List<MedicineRequest> A list of all the medicine requests.
     */
    public List<MedicineRequest> getAllMedicineRequests() {
        return medicineRequests;
    }

    /**
     * Get a medicine request by its unique ID.
     *
     * @param id The ID of the medicine request.
     * @return MedicineRequest The medicine request with the specified ID, or null if not found.
     */
    public MedicineRequest getMedicineRequestById(int id) {
        for (MedicineRequest medicineRequest : medicineRequests) {
            if (medicineRequest.getId() == id) {
                return medicineRequest;
            }
        }
        return null;
    }

    /**
     * Approve a medicine request by changing its status to "APPROVED".
     *
     * @param id The ID of the medicine request to approve.
     */
    public void approveMedicineRequest(int id) {
        MedicineRequest medicineRequest = getMedicineRequestById(id);
        if (medicineRequest != null) {
            medicineRequest.setStatus("APPROVED");
            saveRequestsToCsv();
        }
    }
}
