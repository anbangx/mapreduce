package hadoop.mapreduce.framework;

import hadoop.mapreduce.io.Split;
import hadoop.mapreduce.type.JobStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 * This is the client which the map-reduce application calls to submit a job.
 */  
public class JobClient {
	
	public static final int JOB_CHECK_PERIODICALLY_TIME = 1000;
	
	public static void submitJobAndWaitForCompletion(Job job) {
		//Ask Job Tracker for a new job Id.
		//Check output specification for the job. Error if output dir already exists.
		
		//Compute input splits for the job.
		
		//Copy the resources needed to run the job. (Jar file, config file, computed splits)
		//Might not be needed if we use AFS.
		
		//Make an RPC call to JobTracker to start the job.
		ClusterConfig clusterConfig = new ClusterConfig(job.getConfigFile());
		
		String host = clusterConfig.getJobTrackerHost();
		int port = clusterConfig.getJobTrackerClientCommPort();
		
		Socket socket = null;
		
		List<Split> splits = computeSplits(job);
    	job.setSplits(splits);
    	
    	//Indicates that requesting new Id
    	job.setId(-1);
    	
    	try {
			socket = new Socket(host, port);
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(job);
			oos.flush();
			
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			JobProgress jobProgress = (JobProgress) ois.readObject();
			
			job.setId(jobProgress.getJobId());
			
			oos.close();
			ois.close();
			socket.close();
			
			//Poll for job status periodically.
            boolean inProgress = true;
            do {
				Thread.sleep(JOB_CHECK_PERIODICALLY_TIME);
            	socket = new Socket(host,port);
            	oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(job);
                oos.flush();
                
                ois = new ObjectInputStream(socket.getInputStream());
                jobProgress = (JobProgress) ois.readObject();
                
                oos.close();
                ois.close();
                socket.close();
            	inProgress = (jobProgress.getState() != JobStatus.JobState.SUCCESS) &&
            			(jobProgress.getState() != JobStatus.JobState.FAILED);
            	System.out.println(jobProgress.getMessage());
            } while (inProgress);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
	}
	
	private static List<Split> computeSplits(Job job) {
		//TODO implement
		return null;
	}
}
