package hadoop.mapreduce.mapred;

import hadoop.mapreduce.io.Context;
import hadoop.mapreduce.io.TextRecordReader;

public abstract class Mapper {
	
	private MapTask mapTask;
    private TextRecordReader reader;
    private Context context;
    
	abstract public void map(String key, String value, Context context);
}
