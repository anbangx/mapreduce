package hadoop.mapreduce.framework;

import java.util.List;

public class ClusterConfig {
	private String configFile;
	
	private String jobTrackerHost;
	private int jobTrackerWorkerCommPort;
	private int jobTrackerClientCommPort;
	private int numWorkers;
	private List<WorkerConfig> workers;
	
	public ClusterConfig(String configFile) {
		this.configFile = configFile;
		
		parseConfigFile();
	}
	
	/**
	 * Parse the configuration file and populate all the 
	 * fields in configuration
	 */
	public void parseConfigFile() {
		
	}

	public String getConfigFile() {
		return configFile;
	}

	public String getJobTrackerHost() {
		return jobTrackerHost;
	}

	public int getJobTrackerWorkerCommPort() {
		return jobTrackerWorkerCommPort;
	}

	public int getJobTrackerClientCommPort() {
		return jobTrackerClientCommPort;
	}

	public int getNumWorkers() {
		return numWorkers;
	}

	public List<WorkerConfig> getWorkers() {
		return workers;
	}
	
}

class WorkerConfig {
	private String hostName;
	private int port;
	
	private String uid; //"hostName:port"
	
	private int numMapSlots;
	private int numReduceSlots;
	
	public WorkerConfig(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
		this.uid = hostName + ":" + port;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getNumMapSlots() {
		return numMapSlots;
	}

	public void setNumMapSlots(int numMapSlots) {
		this.numMapSlots = numMapSlots;
	}

	public int getNumReduceSlots() {
		return numReduceSlots;
	}

	public void setNumReduceSlots(int numReduceSlots) {
		this.numReduceSlots = numReduceSlots;
	}
	
}
