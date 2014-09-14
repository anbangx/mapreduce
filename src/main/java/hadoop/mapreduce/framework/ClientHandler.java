package hadoop.mapreduce.framework;

import hadoop.mapreduce.type.Job;
import hadoop.mapreduce.type.JobStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable{
	
	JobTracker jobTracker;

    public ClientHandler(JobTracker jobTracker){
        this.jobTracker = jobTracker;
    }
    
    public void run() {
    	ServerSocket server = null;
		try {
			server = new ServerSocket(jobTracker.getClientCommPort());
			Socket clientSocket;
			
			while(true) {
	    		clientSocket = server.accept();
	    		ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
				
				Job job = (Job)ois.readObject();
				
				if(job.getId() == -1) {
					job.setId(JobTracker.getNextJobId());
					setUpJob(job);
				}
				
				JobStatus status = jobTracker.getStatuses().get(job.getId());
				JobProgress jobProgress = new JobProgress(job.getId());
				
				jobProgress.setPercentMapTaskFinished(0);
				jobProgress.setPercentReduceTaskFinished(0);
				jobProgress.setState(status.getJobState());
				jobProgress.setMessage(status.getMessage());
				
				ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
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
    
    private void setUpJob(Job job){
    	//Create the job status object.
        JobStatus js = new JobStatus(job);
    }
}
