package hadoop.mapreduce.framework;

import hadoop.mapreduce.type.Job;
import hadoop.mapreduce.type.Task;

public class ReduceTask extends Task {
	
	//This is the partitionNumber for the reducce task. It is also
	//like an id since it is unique.
	int partitionNumber;
			
	public ReduceTask(Job parentJob, int taskId, int partitionNumber) {
		super(parentJob, taskId, TaskType.REDUCE);
		this.partitionNumber = partitionNumber;
	}

}
