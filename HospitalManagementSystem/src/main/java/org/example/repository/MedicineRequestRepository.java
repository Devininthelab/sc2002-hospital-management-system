package org.example.repository;

import org.example.entity.Medicine;
import org.example.entity.MedicineRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MedicineRequestRepository is responsible for managing the requests for medicines.
 * It reads from the medicine_request database and updates the status of medicine requests.
 * It also provides methods to add a new medicine request, get all medicine requests, get a medicine request by ID,
 * and approve a medicine request.
 */
public class MedicineRequestRepository {
    private static int counter;
    private List<MedicineRequest> medicineRequests;
    private String csvPath;

    /**
     * Constructor to create a MedicineRequestRepository.
     * @param filePath The writable path to the medicine request database.
     */
    public MedicineRequestRepository(String filePath) {
        this.csvPath = filePath;
        medicineRequests = new ArrayList<>();
        loadRequestsFromCsv();
        counter = highestId();
    }

    /**
     * Get the highest ID among all medicine requests.
     * Used to generate a new unique ID for a new medicine request.
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
     * If the file exists in the writable path, it will load from the file.
     * If the file does not exist, it will load from the resources folder.
     * Reads the CSV file, parses each line, and creates MedicineRequest objects to store in the repository.
     */
    public void loadRequestsFromCsv() {
        InputStream inputStream;
        boolean loadedFromResources = false;
        try {
            File writableFile = new File(csvPath);
            if (writableFile.exists()) {
                inputStream = new FileInputStream(csvPath);
            } else {
                inputStream = getClass().getClassLoader().getResourceAsStream(new File(csvPath).getName());
                if (inputStream == null) {
                    System.err.println("File not found: " + csvPath);
                    return;
                }
                loadedFromResources = true;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                String header = br.readLine(); // Skip header
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    int id = Integer.parseInt(data[0].trim());
                    String[] medicineNames = data[1].trim().split(";");
                    String status = data[2].trim().toUpperCase();
                    medicineRequests.add(new MedicineRequest(id, status, Arrays.asList(medicineNames)));
                }
            }

            if (loadedFromResources) {
                saveRequestsToCsv();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + csvPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number");
        }

        //try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
        //    String line;
        //    String header = br.readLine(); // Skip header
        //    while ((line = br.readLine()) != null) {
        //        String[] data = line.split(",");
        //        int id = Integer.parseInt(data[0].trim());
        //        String[] medicineNames = data[1].trim().split(";");
        //        String status = data[2].trim().toUpperCase();
        //        medicineRequests.add(new MedicineRequest(id, status, Arrays.asList(medicineNames)));
        //    }
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //} catch (Exception e) {
        //    System.out.println("Error parsing medicine request: " + e.getMessage());
        //}
    }

    /**
     * Save all medicine requests to a CSV file.
     * Writes the medicine requests to the writable path.
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
     * With auto-generated ID and status "SUBMITTED".
     * @param medicineNames The list of medicine names requested.
     */
    public void addMedicineRequest(List<String> medicineNames) {
        MedicineRequest medicineRequest = new MedicineRequest(counter++, "SUBMITTED", medicineNames);
        medicineRequests.add(medicineRequest);
        saveRequestsToCsv();
    }

    /**
     * Get all medicine requests from the repository.
     * Used to display all medicine requests to the admin.
     * @return List<MedicineRequest> A list of all the medicine requests.
     */
    public List<MedicineRequest> getAllMedicineRequests() {
        return medicineRequests;
    }

    /**
     * Get a medicine request by its unique ID.
     * Used to retrieve a specific medicine request for viewing or approval.
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
     * Saves the updated status to the CSV file.
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
