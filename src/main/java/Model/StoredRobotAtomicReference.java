import java.util.concurrent.atomic.AtomicReference;

public class StoredRobotAtomicReference
{
	private AtomicReference<Robot> atomicReference;
	
	public StoredRobotAtomicReference()
	{
		atomicReference =  new AtomicReference<Robot>();
	}
	
	public StoredRobotAtomicReference(Robot inRobot)
	{
		if (inRobot != null)
		{
			atomicReference = new AtomicReference<Robot>(inRobot);
		}
		else
		{
			atomicReference = new AtomicReference<Robot>();
		}
	}
	
	public boolean setStoredRobot(Robot inRobot)
	{
		//Check if the atomicReference is null, if so there are no robots stored and we can
		//store a Robot.
		
		boolean setStatus = false;
		if (inRobot != null)
		{
			if (atomicReference.get() == null)
			{
				atomicReference.set(inRobot);
				if (atomicReference.get().getUniqueID() == (inRobot.getUniqueID()))
				{
					setStatus = true;
				}
			}
		}
		return setStatus;
	}
	
	public Robot getStoredRobot()
	{
		return atomicReference.get();
	}
	
	public void clearStoredRobot()
	{
		atomicReference.set(null);
		atomicReference = null;
	}
}	

