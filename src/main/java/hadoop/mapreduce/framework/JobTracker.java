package hadoop.mapreduce.framework;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Queue;

import hadoop.mapreduce.type.ClusterConfig;
import hadoop.mapreduce.type.JobStatus;
import hadoop.mapreduce.type.Task;
import hadoop.mapreduce.type.TaskTrackerHB;
import hadoop.mapreduce.type.TaskTrackerHBResponse;

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
	
	private int workerCommPort;
	
	public JobTracker(ClusterConfig clusterConfig) {
		this.clusterConfig = clusterConfig;
	}
	
	public void run() {
        System.out.println("JobTracker Started");
        
        // Launch thread that will communicate with the clients.
  		Thread clientThread = new Thread(new ClientHandler(this));
  		clientThread.start();
  		
  		// set up loop to communicate with workers.
    	ServerSocket server = null;
    		
    	Socket clientSocket;
    	try {
    		server = new ServerSocket(workerCommPort);
    	} catch (Exception e) {
    		System.out.println("Cannot start job tracker. Quitting ...");
    		e.printStackTrace();
    		
    	}
    		
    	while(true) {
    		try {
	    		clientSocket = server.accept();
	    		ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
				
	    		TaskTrackerHB taskTrackerHB = (TaskTrackerHB)ois.readObject();

	    		TaskTrackerHBResponse resp = handleHeartbeat(taskTrackerHB);
	    		
				ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
				oos.writeObject(resp);
				oos.flush();
				
				ois.close();
				oos.close();
				clientSocket.close();
				
//				checkStaleHosts();
    		} catch (Exception e) {
    			e.printStackTrace();
    		} 
    	}
	}
	
	private TaskTrackerHBResponse handleHeartbeat(TaskTrackerHB hb){
		
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
