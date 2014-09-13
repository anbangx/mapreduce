package hadoop.mapreduce.framework;

import hadoop.mapreduce.type.Job;
import hadoop.mapreduce.type.Task;
import hadoop.mapreduce.type.TaskType;

public class ReduceTask extends Task {

	public ReduceTask(Job parentJob, int taskId, TaskType type) {
		super(parentJob, taskId, type);
		// TODO Auto-generated constructor stub
	}

}
