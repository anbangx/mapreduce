package hadoop.mapreduce.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextRecordReader implements RecordReader {
	private String keyDelimiter;
	private BufferedReader bufferedReader;
	private long start;
	private long end;
	private long pos;

	/**
	 * if keyDelimiter is null then "<fileLocation>:<byteOffset>" will be the
	 * default key else should be <key>:<value>
	 * 
	 * @throws IOException
	 */
	public TextRecordReader(FilePartition filePartition, String keyDelimiter)
			throws IOException {
		this.keyDelimiter = keyDelimiter;
		this.bufferedReader = new BufferedReader(new FileReader(
				filePartition.getFileName()));
		this.start = filePartition.getStart();
		this.end = filePartition.getEnd();
		this.pos = this.start;
		this.bufferedReader.skip(start);
	}

	public Record<String, String> readNextRecord() throws IOException {
		long key = pos;
		Record<String, String> record = null;
		StringBuffer value = new StringBuffer();
		int c;
		outer: while ((c = bufferedReader.read()) != -1) {
			char character = (char) c;

			if (Character.toString(character).equals(keyDelimiter)) {
				break outer;
			}

			value.append((char) c);
			pos++;

		}
		record = new Record<String, String>(Long.toString(key),
				value.toString());
		value = new StringBuffer();
		pos++;
		return record;
	}

	public boolean hasNext() {
		return pos <= end ? true : false;
	}

	public long getFilePosition() {
		return this.pos;
	}

	public void close() throws IOException {
		this.bufferedReader.close();
	}

	/**
	 * For testing!
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String path = "README.md";
		File file = new File(path);
		FilePartition fp = new FilePartition(path, 0, file.length());
		TextRecordReader reader = new TextRecordReader(fp, " ");
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
