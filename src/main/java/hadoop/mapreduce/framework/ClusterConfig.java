package hadoop.mapreduce.framework;

public class ClusterConfig {
	
	private String configFile;
	
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
}
