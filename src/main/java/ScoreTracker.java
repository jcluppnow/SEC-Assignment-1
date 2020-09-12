public class ScoreTracker implements Runnable
{
	private MainController mainController;
	private int score;
	private Object mutex = new Object();
	
	public ScoreTracker(MainController inMainController) throws ScoreTrackerException
	{
		if (inMainController == null)
		{
			throw new ScoreTrackerException("Main Controller is null - Inside ScoreTracker Constructor.");
		}
		else
		{
			score = 0;
			mainController = inMainController;
		}
	}
	
	@Override
	public void run()
	{
		//This is the task.
		try
		{
			while (true)
			{
				synchronized(mutex)
				{
					score += 10;
					//Should this line be inside or outside?
					//Keep it inside therefore the score can be consistent.
					mainController.updateScore(score);
				}
				Thread.sleep(1000);
			}
		}
		catch (InterruptedException interruptedException)
		{
			//Do something
		}
	}
	
	public void destroyedRobot(Robot inRobot, long destructionTime)
	{
		//This is where we add the worth of the Robot to the Score
		//Using the formula - 10 + 100 (t/d) where d is the Robots delay value
		//and t is the players time delay, which is the delay between when the click is
		//made and when the click is executed.
	
		//Since multiple threads could be accessing score we want to synchronise it.
		//Threads that could be accessing this include, Current Thread in Score Tracker & 
		//Main Controller which will create a new task of to calcuate the score which
		//will call this method.
		
		//Calculate Bonus -- do this outside of synchronized want to minimize code inside
		//synchronized statement.
		
		score += calculateRobotBonus(inRobot, destructionTime);
		synchronized(mutex)
		{
			//Tell Main Controller to notify UI that score has changed.
			mainController.updateScore(score);
		}
	}
	
	public int calculateRobotBonus(Robot inRobot, long inDestructionTime)
	{
		//Typecast to integer to prevent possible lossy conversion.
		return (int) (10 + (100 * (inDestructionTime / inRobot.getDelay())));
	}
}