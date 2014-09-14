package hadoop.mapreduce.type;

import hadoop.mapreduce.framework.JobTracker;
import hadoop.mapreduce.framework.MapTask;
import hadoop.mapreduce.framework.ReduceTask;
import hadoop.mapreduce.io.Split;

import java.util.TreeMap;

public class JobStatus {

	public enum JobState {
		PENDING, MAP_RUNNING, MAP_FINISHED, REDUCE_RUNNING, SUCCESS, FAILED
	}

	private JobState jobState;

	private Job job;
	private TreeMap<Integer, Task> mapTasks;
	private TreeMap<Integer, Task> reduceTasks;

	private String message;

	public JobStatus(Job job) {
		this.job = job;
		this.mapTasks = new TreeMap<Integer, Task>();
		this.reduceTasks = new TreeMap<Integer, Task>();
	}

	public int setMapTasks(Job job) {
		for (Split sp : job.getSplits()) {
			MapTask task = new MapTask(job, JobTracker.getNextTaskId(), sp);
			this.mapTasks.put(task.getTaskId(), task);
		}
		return this.mapTasks.size();
	}

	public int setReduceTasks(Job job) {
		for (int i = 0; i < job.getNumReducers(); i++) {
			ReduceTask task = new ReduceTask(job, JobTracker.getNextTaskId(), i);
			this.reduceTasks.put(task.getTaskId(), task);
		}
		return this.reduceTasks.size();
	}

	public synchronized JobState getJobState() {
		return jobState;
	}

	public synchronized void setJobState(JobState jobState) {
		this.jobState = jobState;
	}

	public TreeMap<Integer, Task> getMapTasks() {
		return mapTasks;
	}

	public TreeMap<Integer, Task> getReduceTasks() {
		return reduceTasks;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
