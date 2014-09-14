package hadoop.mapreduce.framework;

import java.util.HashMap;
import java.util.Queue;

import hadoop.mapreduce.type.ClusterConfig;
import hadoop.mapreduce.type.JobStatus;
import hadoop.mapreduce.type.Task;

/**
 * JobTracker starts and maintains the MapReduce Job.
 * JobTracker runs on master node and is responsible for scheduling tasks to different workers
 * It also has other responsibilities like monitoring, failure recovery etc. 
 */
public class JobTracker {
	
	private ClusterConfig clusterConfig;
	
	private static int nextJobId = 1;
	private static int nextTaskId = 1;
	
	public HashMap<Integer, JobStatus> statuses;
	private Queue<Task> pendingMapTasks;
	private Queue<Task> pendingReduceTasks;
	
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
	
	static int getNextJobId(){
		return nextJobId++;
	}
	
	public static synchronized int getNextTaskId(){
        return nextTaskId++;
    }

	public HashMap<Integer, JobStatus> getStatuses() {
		return statuses;
	}

	public Queue<Task> getPendingMapTasks() {
		return pendingMapTasks;
	}

	public Queue<Task> getPendingReduceTasks() {
		return pendingReduceTasks;
	}
	
}
