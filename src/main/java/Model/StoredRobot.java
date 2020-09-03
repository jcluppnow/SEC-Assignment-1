public class StoredRobot
{
	private Robot currentRobot;
	private Object mutex = new Object();
	
	public StoredRobot()
	{
		currentRobot = null;
	}
	
	public StoredRobot(Robot inRobot)
	{
		currentRobot = inRobot;
	}
	
	public boolean setRobot(Robot inRobot)
	{
		boolean setStatus = false;
		synchronized(mutex)
		{
			if (currentRobot == null)
			{
				currentRobot = inRobot;
				setStatus = true;
			}
		}
		
		return setStatus;
	}
	
	public void clearRobot()
	{
		synchronized(mutex)
		{
			currentRobot = null;
		}
	}
}

