import java.util.LinkedList;
import java.util.Queue;

public class AppointmentOutcomeRecordQueue {


    private Queue<AppointmentOutcomeRecord> queue;

    public AppointmentOutcomeRecordQueue() {
        // Initialize the queue
        queue = new LinkedList<>();
    }
    public String getName() {
        return "AppointentOutcomeRecordQueue";
    }
    
    
    // Method to add a AppointmentOutcomeRecord to the queue
    public void addRecord(AppointmentOutcomeRecord record) {
        queue.add(record);
        System.out.println("Added to queue: " + record);
    }

    // Method to display the queue contents
    public void displayQueue() {
        System.out.println("Current queue: " + queue);
    }

    // Method to remove a AppointmentOutcomeRecord from the queue // TODO - need to include the STATUS change of the appointment class
    public AppointmentOutcomeRecord removeRecord() {
    	AppointmentOutcomeRecord record = queue.poll();
        if (record != null) {
        	
            System.out.println("Removed from queue: " + record);
        } else {
            System.out.println("Queue is empty!");
        }
        return record;
    }

}
