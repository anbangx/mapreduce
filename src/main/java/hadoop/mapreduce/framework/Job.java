package hadoop.mapreduce.framework;

import hadoop.mapreduce.io.Split;

import java.util.List;

/**
 * Represents a Map Reduce Job.
 * It contains all information like number of tasks, whether they are completed or running, where are
 * they running, etc 
 */
public class Job {
	private int id;
	
	private int numReducers;
	private String jar;
	private String mapClass;
	private String combinerClass;
	private String reduceClass;
	private String configFile;
	private String inputDir;
	private String outputDir;

	private List<Split> splits;
	
	public Job() {
		this.id = -1;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getNumReducers() {
		return numReducers;
	}

	public String getJar() {
		return jar;
	}

	public String getMapClass() {
		return mapClass;
	}

	public String getCombinerClass() {
		return combinerClass;
	}

	public String getReduceClass() {
		return reduceClass;
	}

	public String getConfigFile() {
		return configFile;
	}

	public String getInputDir() {
		return inputDir;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public List<Split> getSplits() {
		return splits;
	}
	
	public String getTmpMapOpDir() {
		return outputDir + "tmp_" + getId() + "/";
	}

	public void setSplits(List<Split> splits) {
		this.splits = splits;
	}
	
}
