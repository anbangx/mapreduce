package hadoop.mapreduce.framework;

import hadoop.mapreduce.type.Task;

/**
 * Launch JVM if required. Send the Map or the Reduce Task to the JVM. 
 * 
 */

public class TaskManager {
	
	private TaskTracker parentTT;
	private int managerId;
	private TaskTracker.WorkerType workerType;
	private int port;
	
	private Task currentTask;
	
	public TaskManager(int managerId, TaskTracker parentTT, 
			TaskTracker.WorkerType type) {
		this.parentTT = parentTT;
		this.managerId = managerId;
		this.workerType = type;
		this.port = parentTT.getPort() + managerId;
	}

	public TaskTracker getParentTT() {
		return parentTT;
	}

	public int getManagerId() {
		return managerId;
	}

	public TaskTracker.WorkerType getWorkerType() {
		return workerType;
	}

	public int getPort() {
		return port;
	}

	public Task getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}
	
}
