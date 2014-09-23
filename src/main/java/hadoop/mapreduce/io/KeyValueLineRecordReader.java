package hadoop.mapreduce.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class KeyValueLineRecordReader implements RecordReader {
	private String keyDelimiter;
	private BufferedReader bufferedReader;
	private long start;
	private long end;
	private long pos;

	public KeyValueLineRecordReader(FilePartition filePartition,
			String keyDelimiter) throws IOException {
		this.keyDelimiter = keyDelimiter;
		this.bufferedReader = new BufferedReader(new FileReader(
				filePartition.getFileName()));
		this.start = filePartition.getStart();
		this.end = filePartition.getEnd();
		this.pos = this.start;
		this.bufferedReader.skip(filePartition.getStart());
	}

	@Override
	public boolean hasNext() {
		return pos < end ? true : false;
	}

	@Override
	public Record<String, String> readNextRecord() throws IOException {
		String line = null;
		Record<String, String> record = null;

		while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
			String[] splits = line.split(keyDelimiter);
			record = new Record<String, String>(splits[0], splits[1]);
			pos++;
		}
		return record;
	}

	@Override
	public long getFilePosition() {
		return this.pos;
	}

	@Override
	public void close() throws IOException {
		this.bufferedReader.close();

	}

	public static void main(String[] args) throws IOException {
		String path = "README.md";
		File file = new File(path);
		FilePartition fp = new FilePartition(path, 0, file.length());
		KeyValueLineRecordReader reader = new KeyValueLineRecordReader(fp, " ");
		try {
			System.out.println("File Length " + file.length());
			Record<String, String> record;
			while (reader.hasNext()) {
				record = reader.readNextRecord();
				System.out.println(record.getKey() + ":" + record.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
	}
}
