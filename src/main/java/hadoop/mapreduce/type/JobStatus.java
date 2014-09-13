package hadoop.mapreduce.type;

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
	
	public synchronized JobState getJobState() {
		return jobState;
	}

	public synchronized void setJobState(JobState jobState) {
		this.jobState = jobState;
	}
}
