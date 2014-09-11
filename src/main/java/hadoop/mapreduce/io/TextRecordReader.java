package hadoop.mapreduce.io;

public class TextRecordReader {
	
	private FilePartition filePartition;
	private String keyDelimiter;
	
	/**
	 * if keyDelimiter is null then "<fileLocation>:<byteOffset>" will be the default key
	 * else should be <key>:<value>
	 */
	public TextRecordReader(FilePartition filePartition, String keyDelimiter) {
		this.filePartition = filePartition;
		this.keyDelimiter = keyDelimiter;
	}
	
	public Record<String, String> readNextRecord(){
		return null;
	}
}
