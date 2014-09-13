package hadoop.mapreduce.framework;

/**
 * This is the client which the map-reduce application calls to submit a job.
 */  
public class JobClient {
	
	public static void submitJobAndWaitForCompletion(Job job) {
		//Ask Job Tracker for a new job Id.
		//Check output specification for the job. Error if output dir already exists.
		
		//Compute input splits for the job.
		
		//Copy the resources needed to run the job. (Jar file, config file, computed splits)
		//Might not be needed if we use AFS.
		
		//Make an RPC call to JobTracker to start the job.
		ClusterConfig clusterConfig = new ClusterConfig(job.getConfigFile());
	}
}
