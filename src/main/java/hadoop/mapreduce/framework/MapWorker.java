package hadoop.mapreduce.framework;

import java.io.File;
import java.io.FileWriter;

public class MapWorker implements Runnable{
	
	private Task task;
	private FileWriter logsWriter;

    public MapWorker(Task task) {
    	this.task = task;
    	try {
    	this.logsWriter = new FileWriter(
    			new File(task.getParentJob().getTmpMapOpDir() + "/logs.txt"));
        this.logsWriter.write("Start MapWorker!" + "\n");
        logsWriter.flush();
        } catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
