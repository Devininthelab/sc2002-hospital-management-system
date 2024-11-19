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

    public int highestId() {
        int highest = 0;
        for (MedicineRequest medicineRequest : medicineRequests) {
            if (medicineRequest.getId() > highest) {
                highest = medicineRequest.getId();
            }
        }
        return highest;
    }

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

    public void addMedicineRequest(List<String> medicineNames) {
        MedicineRequest medicineRequest = new MedicineRequest(counter++, "SUBMITTED", medicineNames);
        medicineRequests.add(medicineRequest);
        saveRequestsToCsv();
    }

    /**
     * Get all medicine requests
     * @return List of MedicineRequest
     */
    public List<MedicineRequest> getAllMedicineRequests() {
        return medicineRequests;
    }


    /**
     * Get medicine request by id
     * @param id
     * @return MedicineRequest
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
     * Approve a medicine request
     * @param id
     */
     public void approveMedicineRequest(int id) {
         MedicineRequest medicineRequest = getMedicineRequestById(id);
         if (medicineRequest != null) {
             medicineRequest.setStatus("APPROVED");
             saveRequestsToCsv();
         }
     }
}
