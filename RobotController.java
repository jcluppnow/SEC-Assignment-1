import java.util.*;
import java.text.DecimalFormat;

public class RobotController implements Runnable
{
	private MainController mainController;
	private Robot myRobot;
	
	public RobotController(MainController inMainController, Robot inRobot)
	{
		if ((inMainController != null) && (inRobot != null))
		{
			mainController = inMainController;
			myRobot = inRobot;
		}
		else
		{	
			//Throw RobotController exception.
		}
	}
	
	@Override
	public void run()
	{
		//When this is created, should this sleep before moving?
		//This is the Robots moving thread.
		//If a location is set - want to be able to set travelling direction
		//Once started and set it to neutral when not moving.
		//Before this happens, currentLocation & destinationLocation must be set.
		try
		{
			//So this Thread will run as long as the Robot remains alive.
			while (myRobot.getAliveStatus() == true)
			{
				boolean setStatus = mainController.setMovingRobotLocation(myRobot);
				
				if (setStatus == true)
				{
					//Doing this here means we only need to calculate moving direction once per move.
					myRobot.setMovingDirection(mainController.getDirection(myRobot));
					
					//System.out.println("RobotDebug: " + myRobot.getUniqueID() + " CL: " + myRobot.getCurrentLocation().locationToString() + "DL: " + myRobot.getDestinationLocation().locationToString());
					//Specify desired Decimal Format.
					DecimalFormat df = new DecimalFormat("#.#");
					
					//Runs 10 times, which is the amount of animation steps.
					for (int i = 0; i < 10; i++)
					{
						//Increase the progress by speed.
						//Ceiling ensures that it equals 1.0 not 0.9999 etc
						//Convert the result after formating from String back to Double.
						myRobot.setProgress(Double.parseDouble(df.format(myRobot.getProgress() + myRobot.getSpeed())));

						//Tell the controller that this Robot has moved, which tells the Arena that
						//a Robot's location has changed.
						mainController.robotHasMoved();
						
						//Only want this loop to run every 50 ms.
						Thread.sleep(50); //myThread?
					}
						
					//Remove this Robot from previous destination.
					mainController.clearRobot(myRobot.getCurrentLocation());
						
					//Once done, set currentLocation as what we just travelled to.
					myRobot.setCurrentLocation(myRobot.getDestinationLocation());
						
					//Reset destination to orignal state.
					myRobot.resetDestinationLocation();
						
					//As travelling has finished, reset movingDirection as we are no longer moving.
					myRobot.setMovingDirection("Neutral");
						
					//Reset the Progress
					myRobot.setProgress(0.0);
						
					//Sleep for delay as moving should only occur after every delay.
					Thread.sleep(myRobot.getDelay()); //myThread??	
				}
				else
				{
					//Do cleanup/status checks.
					myRobot.cleanUp();
				}
			}
		}
		catch (InterruptedException interruptedException)
		{
			//Can do 2 things, either end the thread or just throw a Robot Exception.
		}

		if (myRobot == null)
		{
			System.out.println("Robot is null");
		}
	}
	
	public boolean matchingRobot(Robot inRobot)
	{
		boolean matchingRobotStatus = false;
		
		if (myRobot.equals(inRobot))
		{
			matchingRobotStatus = true;
		}
		
		return matchingRobotStatus;
	}
	
	public void stopThread()
	{
		//Marks the Robot as not alive anymore.
		myRobot.setDead();
		
		//Set the Robot to null.
		myRobot = null;
	}
		
}