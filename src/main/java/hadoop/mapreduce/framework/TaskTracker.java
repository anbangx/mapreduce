package hadoop.mapreduce.framework;

/**
 * This process manages tasks on the local machine. It periodically sends heartbeats
 * to the JobTracker,  which in return message sends its tasks to run.
 * The Map and reduce tasks should be run in a separate JVM so that the failure 
 * of any particular task does not bring down the TaskTracker.
 * The TaskRunner thread takes care of launching and monitoring the 
 * the JVM  
 */
public class TaskTracker {

}
