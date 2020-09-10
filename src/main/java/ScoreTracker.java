public class ScoreTracker implements Runnable
{
	private MainController mainController;
	private int score;
	private Object mutex = new Object();
	
	public ScoreTracker(MainController inMainController)
	{
		score = 0;
		mainController = inMainController;
	}
	
	public void run()
	{
		//This is the task.
		try
		{
			while (true)
			{
				synchronized(mutex);
				{
					score += 10;
					//Should this line be inside or outside?
					mainController.updateScore(score);
				}
				Thread.sleep(1000);
			}
		}
	}
	
	public void destroyedRobot(Robot inRobot)
	{
		//This is where we add the worth of the Robot to the Score
		//Using the formula - 10 + 100 (t/d) where d is the Robots delay value
		//and t is the players time delay, which is the delay between when the click is
		//made and when the click is executed.
	
		//Since multiple threads could be accessing score we want to synchronise it.
		//Threads that could be accessing this include, Current Thread in Score Tracker & 
		//Main Controller which will create a new task of to calcuate the score which
		//will call this method.
		synchronized(mutex)
		{
			score = score + 10;
		}
	}