import java.util.LinkedList;
import java.util.Queue;

public class RejectedQueue { // to top up inventory and meet the request
	private Queue<AppointmentOutcomeRecord> queue;
    public RejectedQueue() {
        // Initialize the queue
        queue = new LinkedList<>();
    }
    
    public String getName() {
        return "RejectedQueue";
    }
    
    public void addRecord(AppointmentOutcomeRecord record) {
    	queue.add(record);
        System.out.println("Adding record with unavailable medication: " + record);      
    }
}
