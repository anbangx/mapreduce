package hadoop.mapreduce.framework;

import hadoop.mapreduce.type.Job;

import java.io.IOException;
import java.io.ObjectInputStream;
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
				
	//				if(job.getId() == -1) {
	//					job.setId(JobTracker.getNextJobId());
	//					setUpJob(job);
	//				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
}
