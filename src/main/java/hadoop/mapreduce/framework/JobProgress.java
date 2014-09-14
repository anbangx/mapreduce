package hadoop.mapreduce.framework;

import hadoop.mapreduce.type.JobStatus;

public class JobProgress {
	
	private int jobId;
	
	private JobStatus.JobState state;
	double percentMapTaskFinished;
    double percentReduceTaskFinished;
    private String message;
	
	public JobProgress(int id) {
		this.jobId = id;
	}
	
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

	public double getPercentMapTaskFinished() {
		return percentMapTaskFinished;
	}

	public void setPercentMapTaskFinished(double percentMapTaskFinished) {
		this.percentMapTaskFinished = percentMapTaskFinished;
	}

	public double getPercentReduceTaskFinished() {
		return percentReduceTaskFinished;
	}

	public void setPercentReduceTaskFinished(double percentReduceTaskFinished) {
		this.percentReduceTaskFinished = percentReduceTaskFinished;
	}
}
