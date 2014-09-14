package hadoop.mapreduce.framework;

import hadoop.mapreduce.type.Job;
import hadoop.mapreduce.type.Task;

public class ReduceTask extends Task {

	public ReduceTask(Job parentJob, int taskId, Task.TaskType type) {
		super(parentJob, taskId, type);
		// TODO Auto-generated constructor stub
	}

}
