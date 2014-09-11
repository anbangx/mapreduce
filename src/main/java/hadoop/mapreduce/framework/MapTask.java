package hadoop.mapreduce.framework;

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
public class MapTask {
	// TODO implement
}
