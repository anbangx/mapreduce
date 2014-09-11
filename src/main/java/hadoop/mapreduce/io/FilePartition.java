package hadoop.mapreduce.io;

/**
 * Represents a partition of file. A "split" which is typically
 * proccessed by one map task might contain multiples of file
 * partitions 
 */
public class FilePartition {
	private String fileName; // File name
	private long start;	// start offset 
	private long end;	// end offset
	
	public FilePartition(String fileName, long start, long end){
		this.fileName = fileName;
		this.start = start;
		this.end = end;
	}
}
