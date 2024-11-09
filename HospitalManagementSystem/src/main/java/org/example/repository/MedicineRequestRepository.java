package org.example.repository;

import org.example.entity.Medicine;
import org.example.entity.MedicineRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicineRequestRepository {
    /**
     * counter pointing to highest current id
     */
    private static int counter;
    private List<MedicineRequest> medicineRequests;
    private final String csvPath = "src/main/resources/Medicine_Request.csv";

    public MedicineRequestRepository() {
        medicineRequests = new ArrayList<>();
        loadRequestsFromCsv();
    }

    public void loadRequestsFromCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                String[] medicineNames = data[1].trim().split(";");
                String status = data[2].trim();
                medicineRequests.add(new MedicineRequest(id, status, Arrays.asList(medicineNames)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error parsing medicine request: " + e.getMessage());
        }
    }

    public void saveRequestsToCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {
            for (MedicineRequest medicineRequest : medicineRequests) {
                String medicineRequestString = String.join(";", medicineRequest.getMedicines());
                bw.write(medicineRequest.getId() + "," + medicineRequestString
                        + "," + medicineRequest.getStatus() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMedicineRequest(List<String> medicineNames) {
        MedicineRequest medicineRequest = new MedicineRequest(counter++, "submitted", medicineNames);
        medicineRequests.add(medicineRequest);
        saveRequestsToCsv();
    }


}
