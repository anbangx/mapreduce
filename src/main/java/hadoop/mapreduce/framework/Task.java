package hadoop.mapreduce.framework;

/**
 * This is the data class representing a specific task in the map-reduce task.
 * An instance of any of its subclass should contain all the information 
 * required to execute this task. For example, MapTask will contain information
 * about which split to work on, the jar file location, the config file location
 * and other such information.
 */
enum TaskState {
	PENDING,
	RUNNING,
	SUCCESS,
	RETRY,
	FAILED
}
enum TaskType {
	MAP,
	REDUCE
}

abstract public class Task {
	// TODO implement
	private Job parentJob;
	private int taskId;
	
	private TaskState state;
	private TaskType taskType;
	private String taskTrackerId;
	
	private double percentComplete;
	private int attemptNum;
	
	public Task(Job parentJob, int taskId, TaskType type) {
		this.parentJob = parentJob;
		this.taskId = taskId;
		this.taskType = type;
		
		this.state = TaskState.PENDING;
		this.percentComplete = 0;
		this.attemptNum = 0;
	}

	public Job getParentJob() {
		return parentJob;
	}

	public int getTaskId() {
		return taskId;
	}

	public TaskState getState() {
		return state;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public String getTaskTrackerId() {
		return taskTrackerId;
	}

	public double getPercentComplete() {
		return percentComplete;
	}

	public int getAttemptNum() {
		return attemptNum;
	}
	
}
