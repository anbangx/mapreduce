package hadoop.mapreduce.type;


public class ReduceTask extends Task {
	
	//This is the partitionNumber for the reducce task. It is also
	//like an id since it is unique.
	int partitionNumber;
			
	public ReduceTask(Job parentJob, int taskId, int partitionNumber) {
		super(parentJob, taskId, TaskType.REDUCE);
		this.partitionNumber = partitionNumber;
	}
	
	// Copy constructor
	public ReduceTask(ReduceTask task) {
		super(task);
		this.partitionNumber = task.getPartitionNumber();
	}

	public int getPartitionNumber() {
		return partitionNumber;
	}

	public void setPartitionNumber(int partitionNumber) {
		this.partitionNumber = partitionNumber;
	}
	
}
