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

	public String getTaskTrackerId() {
		return taskTrackerId;
	}

	public void setTaskTrackerId(String taskTrackerId) {
		this.taskTrackerId = taskTrackerId;
	}

	public int getNumFreeMapSlots() {
		return numFreeMapSlots;
	}

	public void setNumFreeMapSlots(int numFreeMapSlots) {
		this.numFreeMapSlots = numFreeMapSlots;
	}

	public int getNumFreeReduceSlots() {
		return numFreeReduceSlots;
	}

	public void setNumFreeReduceSlots(int numFreeReduceSlots) {
		this.numFreeReduceSlots = numFreeReduceSlots;
	}

	public boolean isInitHB() {
		return isInitHB;
	}

	public void setInitHB(boolean isInitHB) {
		this.isInitHB = isInitHB;
	}

	public List<Task> getTasksSnapshot() {
		return tasksSnapshot;
	}

	public void setTasksSnapshot(List<Task> tasksSnapshot) {
		this.tasksSnapshot = tasksSnapshot;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
	
}
