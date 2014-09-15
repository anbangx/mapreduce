package hadoop.mapreduce.type;

import java.util.List;

/**
 * This is the heartbeat message task tracker sends to job tracker.
 *
 */
public class TaskTrackerHB {
	private String taskTrackerId;
	private int numFreeMapSlots;
	private int numFreeReduceSlots;
	private boolean isInitHB;
	
	//These consists all statistics about the map / reduce tasks
	List<Task> tasksSnapshot;
	
	private long sendTime;
	
	public TaskTrackerHB(String taskTrackerId, int numFreeMapSlots, 
			int numFreeReduceSlots, boolean isInitHB, List<Task> taskSnapshot) {
		this.taskTrackerId = taskTrackerId;
		this.numFreeMapSlots = numFreeMapSlots;
		this.numFreeReduceSlots = numFreeReduceSlots;
		this.isInitHB = isInitHB;
		this.tasksSnapshot = taskSnapshot;
		this.sendTime = System.currentTimeMillis();
	}
}
