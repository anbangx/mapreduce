package hadoop.mapreduce.framework;

import hadoop.mapreduce.type.Job;
import hadoop.mapreduce.type.JobStatus;
import hadoop.mapreduce.type.JobStatus.JobState;
import hadoop.mapreduce.type.Task;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable {

	JobTracker jobTracker;

	public ClientHandler(JobTracker jobTracker) {
		this.jobTracker = jobTracker;
	}

	public void run() {
		ServerSocket server = null;
		try {
			server = new ServerSocket(jobTracker.getClientCommPort());
			Socket clientSocket;

			while (true) {
				clientSocket = server.accept();
				ObjectInputStream ois = new ObjectInputStream(
						clientSocket.getInputStream());

				Job job = (Job) ois.readObject();

				if (job.getId() == -1) {
					job.setId(JobTracker.getNextJobId());
					setUpJob(job);
				}

				JobStatus status = jobTracker.getStatuses().get(job.getId());
				JobProgress jobProgress = new JobProgress(job.getId());

				jobProgress.setPercentMapTaskFinished(0);
				jobProgress.setPercentReduceTaskFinished(0);
				jobProgress.setState(status.getJobState());
				jobProgress.setMessage(status.getMessage());

				ObjectOutputStream oos = new ObjectOutputStream(
						clientSocket.getOutputStream());
				oos.writeObject(jobProgress);

				oos.flush();

				ois.close();
				oos.close();
				clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setUpJob(Job job) {
		// Create the job status object.
		JobStatus js = new JobStatus(job);

		// set up the mapTasks and reduceTasks
		int numMapTasks = js.setMapTasks(job);
		int numReduceTasks = js.setReduceTasks(job);

		// Make tmp directories in the shared file system
		if(!makeTmpDirectories(job))
			System.out.println("Error when make a tmp dir in setUpJob!");
		
		// set the job state in jobStatus
		js.setJobState(JobState.PENDING);
		System.out.println("Received new job from client. Job Id = "
				+ job.getId() + "Num Map Tasks = " + numMapTasks
				+ " Num Reduce partitions " + numReduceTasks);

		// insert job status into JobTracker
		jobTracker.getStatuses().put(job.getId(), js);

		// insert all task into pending queue
		for (Task task : js.getMapTasks().values())
			jobTracker.getPendingMapTasks().add(task);
	}
	
	private boolean makeTmpDirectories(Job job){
		File file = new File(job.getOutputDir());
		if (!file.isDirectory() || !file.exists()) {
			System.out.println("Error: Output directory doesn't exist!");
			return false;
		} else {
			File tmpDir = new File(job.getTmpMapOpDir());
			System.out.println("Creating temporary directory "
					+ job.getTmpMapOpDir());
			tmpDir.mkdirs();
		}
		return true;
	}
}
