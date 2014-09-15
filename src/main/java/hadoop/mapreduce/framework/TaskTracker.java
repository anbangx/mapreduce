package hadoop.mapreduce.framework;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import hadoop.mapreduce.type.ClusterConfig;
import hadoop.mapreduce.type.Task;
import hadoop.mapreduce.type.TaskTrackerHB;
import hadoop.mapreduce.type.TaskTrackerHBResponse;
import hadoop.mapreduce.type.WorkerConfig;

/**
 * This process manages tasks on the local machine. It periodically sends heartbeats
 * to the JobTracker,  which in return message sends its tasks to run.
 * The Map and reduce tasks should be run in a separate JVM so that the failure 
 * of any particular task does not bring down the TaskTracker.
 * The TaskRunner thread takes care of launching and monitoring the 
 * the JVM  
 */
public class TaskTracker {
	public enum WorkerType {
		MAPPER,
		REDUCER
	}
	
	public static final long JOB_TRACKER_HB_TIME = 500;
	
	private String id;
	private int port;
	
	private ClusterConfig clusterConfig;
	
	private HashMap<Integer, TaskManager> taskManagers;
	private int taskMgrCounter;
	
	private ConcurrentHashMap<Integer, Task> runningTaskStats;
	
	public TaskTracker(int port, ClusterConfig clusterConfig) {
		this.port = port;
		this.clusterConfig = clusterConfig;
		
		taskMgrCounter = 1;
		taskManagers = new HashMap<Integer, TaskManager>();
		runningTaskStats = new ConcurrentHashMap<Integer, Task>();
		
		try {
			this.id = InetAddress.getLocalHost().getHostName() + ":" + port;
		} catch (Exception e) {
			System.out.println("Cannot get task tracker hostname ... Exitting");
			e.printStackTrace();
			System.exit(2);
		}
		WorkerConfig workerConfig = clusterConfig.getWorkerConfig(id);
		
		//Initialize the Mappers
		for(int i = 0; i < workerConfig.getNumMapSlots(); i++) {
			int id = this.taskMgrCounter++;
			TaskManager mgr = new TaskManager(id, this, WorkerType.MAPPER);
			this.taskManagers.put(id, mgr);
		}
		
		//Initialize the Reducers
		for(int i = 0; i < workerConfig.getNumReduceSlots(); i++) {
			int id = this.taskMgrCounter++;
			TaskManager mgr = new TaskManager(id, this, WorkerType.REDUCER);
			this.taskManagers.put(id, mgr);
		}
	}
	
	/**
	 * Send heartbeats to JobTracker with current status and based on
	 * response from the JobTracker, perform required actions
	 */	
	public void run() throws Exception {

        System.out.println("TaskTracker Running");
		
		while(true) {
			Socket socket = null;
			try {
				socket = new Socket(clusterConfig.getJobTrackerHost(), 
						clusterConfig.getJobTrackerWorkerCommPort());
				
				//Send heart-beat to job tracker.
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				
				List<Task> taskSnapshot = createTaskSnapshot();
				TaskTrackerHB hb = new TaskTrackerHB(id, getFreeMapperSlots(), getFreeReducerSlots(), 
						false, taskSnapshot);
				oos.writeObject((Object)hb);
				oos.flush();
				
				//Receive response from job tracker.
		        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		        TaskTrackerHBResponse resp = (TaskTrackerHBResponse)ois.readObject();
		        
		        //Handle the response.
		        handleResponse(resp);
		
				oos.close();
				ois.close();
			
			} finally {
				if(socket != null){
					socket.close();
				}
			}
			
			// Sleep for 1 second
			Thread.sleep(JOB_TRACKER_HB_TIME);
		}
		
	}
	
	private void handleResponse(TaskTrackerHBResponse resp) {
		
	}
	
	/**
	 * Create a snapshot of the current running tasks.
	 * @return
	 */
	private List<Task> createTaskSnapshot() {
		List<Task> taskSnapshot = new ArrayList<Task>();
		List<Task> finishedTasks = new ArrayList<Task>();
		
		for(Task task : runningTaskStats.values()) {
			if(task instanceof MapTask) {
				taskSnapshot.add(new MapTask((MapTask)task));
			} else if(task instanceof ReduceTask) {
				taskSnapshot.add(new ReduceTask((ReduceTask)task));
			}
			
			if(task.getPercentComplete() == 100) {
				finishedTasks.add(task);
			}
			
		}
		
		//cleanup tasks which have finished.
		for(Task task : finishedTasks) {
			runningTaskStats.remove(task.getTaskId());
		}
		
		return taskSnapshot;
	}
	
	private int getFreeMapperSlots() {
		int freeMapSlots = 0;
		for(TaskManager mgr : taskManagers.values()) {
			if(mgr.getWorkerType() == WorkerType.MAPPER && mgr.getCurrentTask() == null) {
				freeMapSlots++;
			}
		}
		return freeMapSlots;
	}
	
	private int getFreeReducerSlots() {
		int freeReduceSlots = 0;
		for(TaskManager mgr : taskManagers.values()) {
			if(mgr.getWorkerType() == WorkerType.REDUCER && mgr.getCurrentTask() == null) {
				freeReduceSlots++;
			}
		}
		return freeReduceSlots;
	}
	
	private TaskManager getFreeWorker(WorkerType type) {
		for(TaskManager mgr : taskManagers.values()) {
			if(mgr.getWorkerType() == type && mgr.getCurrentTask() == null) {
				return mgr;
			}
		}
		return null;
	}
	
	public String getId() {
		return id;
	}

	public int getPort() {
		return port;
	}

	public ClusterConfig getClusterConfig() {
		return clusterConfig;
	}

	public HashMap<Integer, TaskManager> getTaskManagers() {
		return taskManagers;
	}

	public int getTaskMgrCounter() {
		return taskMgrCounter;
	}

	public ConcurrentHashMap<Integer, Task> getRunningTaskStats() {
		return runningTaskStats;
	}

	public static void main(String[] args) {
		ClusterConfig clusterConfig;
		// Read Config to get the location of JobTracker
		int port = Integer.parseInt(args[0]);
		clusterConfig = new ClusterConfig(args[1]);
		
		TaskTracker taskTracker = new TaskTracker(port, clusterConfig);
		
	}
}
