package hadoop.mapreduce.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LineRecordReader {
	private long start;
	private long pos;
	private long end;
	int maxLength = Integer.MAX_VALUE;
	private String path;
	private String delimiter;
	private BufferedReader bufferedReader;

	public LineRecordReader(String path, long start, long end, String delimiter)
			throws FileNotFoundException {
		this.start = start;
		this.pos = start;
		this.end = end;
		this.delimiter = delimiter;
		this.bufferedReader = new BufferedReader(new FileReader(path));
	}

	public long getPosition() {
		return pos;
	}

	public synchronized boolean next(long key, String value) {
		while (getPosition() <= end) {
			key = pos;

			int newSize = 0;
			try {
				value = bufferedReader.readLine();
				newSize = value.length();

				if (newSize < maxLength) {
					pos += newSize;
					return true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}

	public void close() throws IOException {
		bufferedReader.close();
	}
}
