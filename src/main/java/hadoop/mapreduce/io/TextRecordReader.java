package hadoop.mapreduce.io;

import java.io.File;

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
		// TODO implement
		return null;
	}
	
	/**
	 * For testing!
	 */
	public static void main(String[] args) {
		try {
		
			String path = "test.txt";
			File file = new File(path);
			FilePartition fp = new FilePartition(path, 13, file.length());
			
			TextRecordReader reader = new TextRecordReader(fp, null);
			Record<String, String> record;
			while( (record = reader.readNextRecord()) != null ) {
				System.out.println(record.getKey() + ":" + record.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
