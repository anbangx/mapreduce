package hadoop.mapreduce.framework;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Queue;

import hadoop.mapreduce.type.ClusterConfig;
import hadoop.mapreduce.type.JobStatus;
import hadoop.mapreduce.type.JobStatus.JobState;
import hadoop.mapreduce.type.Task;
import hadoop.mapreduce.type.Task.TaskType;
import hadoop.mapreduce.type.TaskTrackerHB;
import hadoop.mapreduce.type.TaskTrackerHBResponse;

/**
 * JobTracker starts and maintains the MapReduce Job.
 * JobTracker runs on master node and is responsible for scheduling tasks to different workers
 * It also has other responsibilities like monitoring, failure recovery etc. 
 * 
 */
public class JobTracker {
	
	private ClusterConfig clusterConfig;
	
	private static int nextJobId = 1;
	private static int nextTaskId = 1;
	
	public HashMap<Integer, JobStatus> statuses;
	private Queue<Task> pendingMapTasks;
	private Queue<Task> pendingReduceTasks;
	private HashMap<String, TaskTrackerHB> lastHeartbeat; 
	
	private int workerCommPort;
	
	public JobTracker(ClusterConfig clusterConfig) {
		this.clusterConfig = clusterConfig;
	}
	
	// TODO Use this to test the communication between Jobtracker and Client
	public void onlyOnlyClientThread(){
		System.out.println("JobTracker Started, but only run client server...");
        
        // Launch thread that will communicate with the clients.
  		Thread clientThread = new Thread(new ClientHandler(this));
  		clientThread.start();
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
		TaskTrackerHBResponse resp = null;
		
        lastHeartbeat.put(hb.getTaskTrackerId(), hb);
		// 1. Update all statuses from the heartbeat
        for(Task t : hb.getTasksSnapshot()) {
			int jobId = t.getParentJob().getId();
			JobStatus jobStatus = statuses.get(jobId);
			boolean isMapTask = (t.getTaskType() == TaskType.MAP);
			if(t.getState() == Task.TaskState.FAILED) {
				//If any task fails more than a certain number then mark job as failed
				if(t.getAttemptNum() > 3) {
					jobStatus.setJobState(JobState.FAILED);
				} else {
					t.setState(Task.TaskState.PENDING);
					t.setPercentComplete(0);
					t.setAttemptNum(t.getAttemptNum() + 1);
					if(isMapTask)
						pendingMapTasks.add(t);
					else
						pendingReduceTasks.add(t);
				}
			} 
			if(isMapTask)
				jobStatus.getMapTasks().put(t.getTaskId(), t);
			else
				jobStatus.getReduceTasks().put(t.getTaskId(), t);
		}
		// TODO 2. Assign new task to the worker
		return null;
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
