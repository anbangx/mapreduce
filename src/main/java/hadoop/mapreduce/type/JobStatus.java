package hadoop.mapreduce.type;

import hadoop.mapreduce.framework.JobTracker;
import hadoop.mapreduce.framework.MapTask;
import hadoop.mapreduce.framework.ReduceTask;
import hadoop.mapreduce.io.Split;

import java.util.ArrayList;
import java.util.List;

public class JobStatus {
	
	public enum JobState {
		PENDING,
		MAP_RUNNING,
        MAP_FINISHED,
        REDUCE_RUNNING,
		SUCCESS,
		FAILED
	}
	private JobState jobState;
	
	private Job job;
	private String message;
	
	public JobStatus(Job job){
		List<Task> mapTasks = getMapTasks(job);
        List<Task> reduceTasks = getReduceTasks(job);
        this.job = job;
	}
	
	private List<Task> getMapTasks(Job job){
        List<Task> tasks = new ArrayList<Task>();
        for(Split sp : job.getSplits()){
            tasks.add(new MapTask(job, JobTracker.getNextTaskId(), sp));
        }
        return tasks;
    }
	
	private List<Task> getReduceTasks(Job job){
        List<Task> tasks = new ArrayList<Task>();
        for(int i = 0 ; i < job.getNumReducers(); i++){
            tasks.add(new ReduceTask(job, JobTracker.getNextTaskId(), i));
        }
        return tasks;
    }
	
	public synchronized JobState getJobState() {
		return jobState;
	}

	public synchronized void setJobState(JobState jobState) {
		this.jobState = jobState;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
