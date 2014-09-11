package hadoop.mapreduce.io;

/**
 * This class represents a split of input data which should be submitted to a Mapper
 * It is comprised of a list of FilePartitions which represent the chunks of 
 * data that this split contains 
 *
 */
public class Split {
	
	FilePartition filePartition;
	
	public Split(FilePartition fp) {
		this.filePartition = fp;
	}
	
	public FilePartition getFilePartition() {
		return filePartition;
	}
	
	@Override
	public String toString() {
		return filePartition.getFileName() + "<" + filePartition.getStart()
				+ "," + filePartition.getEnd() + ">";
	}
}
