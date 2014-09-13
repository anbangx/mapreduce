package hadoop.mapreduce.framework;

import hadoop.mapreduce.type.JobStatus;

public class JobProgress {
	
	private int jobId;
	
	private String message;
	private JobStatus.JobState state;
	
	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public JobStatus.JobState getState() {
		return state;
	}

	public void setState(JobStatus.JobState state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
