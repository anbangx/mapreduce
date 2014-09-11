package hadoop.mapreduce.io;

public class TextRecordWriter {
	
	private String outputFile;
	private String delimiter;
	
	public TextRecordWriter(String outputFile, String delimiter) {
		this.outputFile = outputFile;
		this.delimiter = delimiter;
	}
	
	public void writeRecord(Record<String, String> record){
		// TODO implement
	}
}
