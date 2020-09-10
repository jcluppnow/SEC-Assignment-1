//Model Class
import java.util.concurrent.LinkedBlockingQueue;

public class ActionsContainer
{
	//Creates at capacity of Integer.MAX_VALUE
	private LinkedBlockingQueue<Action> queue = new LinkedBlockingQueue<>();
	
	public void putNextAction(Action inAction) throws InterruptedException
	{
		//Don't insert null objects into Queue.
		if (inAction != null)
		{
			//Inserts inAction into tail of queue.
			queue.put(inAction);
		}
	}
	
	public Action getNextAction() throws InterruptedException
	{
		//Retrieves & removes the head of the queue, waiting if necessary
		//until an element becomes available.
		return queue.take();
	}