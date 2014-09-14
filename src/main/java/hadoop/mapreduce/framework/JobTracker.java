package hadoop.mapreduce.framework;

import hadoop.mapreduce.type.ClusterConfig;

/**
 * JobTracker starts and maintains the MapReduce Job.
 * JobTracker runs on master node and is responsible for scheduling tasks to different workers
 * It also has other responsibilities like monitoring, failure recovery etc. 
 */
public class JobTracker {
	
	private ClusterConfig clusterConfig;
	
	public JobTracker(ClusterConfig clusterConfig) {
		this.clusterConfig = clusterConfig;
	}
	
	public void run() {
        System.out.println("JobTracker Started");
        
        // Launch thread that will communicate with the clients.
  		Thread clientThread = new Thread(new ClientHandler(this));
  		clientThread.start();
	}
	
	public int getClientCommPort() {
		return clusterConfig.getJobTrackerClientCommPort();
	}
}
