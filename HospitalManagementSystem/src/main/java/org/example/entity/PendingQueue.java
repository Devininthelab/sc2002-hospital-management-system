import java.util.LinkedList;
import java.util.Queue;

public class PendingQueue extends AppointmentOutcomeRecordQueue {
	private Queue<AppointmentOutcomeRecord> queue;
    public PendingQueue() {
        // Initialize the queue
        queue = new LinkedList<>();
    }
    public String getName() {
        return "PendingQueue";
    }
    public void addRecord(AppointmentOutcomeRecord record) {
    	queue.add(record);
        System.out.println("Adding record with pending medication: " + record);
    }
}
