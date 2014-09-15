package hadoop.mapreduce.framework;

import hadoop.mapreduce.io.Split;
import hadoop.mapreduce.type.Job;
import hadoop.mapreduce.type.Task;
import hadoop.mapreduce.type.Task.TaskType;

/**
 * Contains all the information that is required for completing
 * a map task.
 * Things required are:
 * 1. Jar file location (available in parentJob)
 * 2. Map Class name. (available in parentJob)
 * 3. Input data to process (Split which contains all the file partitions)
 * 4. Output directory location (from parentJob and taskId)
 * 5. Map Input key value and output key value (available in parentJob)
 * 6. Combiner class (available in parentJob). 
 */
public class MapTask extends Task {

	private static final long serialVersionUID = 1L;

	private Split split;
	
	public MapTask(Job parentJob, int taskId, Split split) {
		super(parentJob, taskId, TaskType.MAP);
		
		this.split = split;
	}
	
	// Copy constructor
	public MapTask(MapTask task) {
		super(task);
		this.split = task.getSplit();
	}

	public Split getSplit() {
		return split;
	}
	
}
