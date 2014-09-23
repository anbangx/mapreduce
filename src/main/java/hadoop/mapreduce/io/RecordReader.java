package hadoop.mapreduce.io;

import java.io.IOException;

public interface RecordReader {
	public boolean hasNext();

	public Record<String, String> readNextRecord() throws IOException;

	public long getFilePosition();

	public void close() throws IOException;
}
