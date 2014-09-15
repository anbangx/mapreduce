package hadoop.mapreduce.type;

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
	
	public WorkerConfig getWorkerConfig(String id) {
		for(WorkerConfig config : this.workers) {
			if(config.getUid().equals(id)) {
				return config;
			}
		}
		return null;
	}
}

