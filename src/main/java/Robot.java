import java.util.*;
import java.text.DecimalFormat;

//Had to make the class cloneable so that I could create a readonly copy.

public class Robot implements Cloneable
{
	private MainController mainController;
    private int uniqueID, delay;
	private Location<Integer> currentLocation, destinationLocation;	//X,Y
	private double speed;
	private double progress;
	private String movingDirection;
	private static final double TOLERANCE = 0.0001;
	
	public Robot(int inID, int inDelay)
	{
		uniqueID = inID;
		delay = inDelay;
		speed = 0.1;
		progress = 0.0;
		movingDirection = "Neutral";
	}
	
    public Robot(int inID, int inDelay, int inRowLocation, int inColumnLocation)
    {
        uniqueID = inID;
        delay = inDelay;
		currentLocation = new Location<Integer>(inRowLocation, inColumnLocation, "Robot: " + uniqueID);
		speed = 0.1;	//This is gotten from dividing how long it takes to travel by frames. 50/500
		progress = 0.0;
		movingDirection = "Neutral";
	}
	
	@Override
	public String toString()
	{
		return "Robot: " + uniqueID + " Delay: " + delay + "CL:" + currentLocation.locationToString() + "DL" + destinationLocation.locationToString() + " Direction: " + movingDirection + " Progress: " + progress;
	}
	
	//Override the inbuilt clone class.
	@Override
	protected Robot clone()
	{
		Robot newRobot = new Robot(uniqueID, delay);
		newRobot.setCurrentLocation(currentLocation);
		newRobot.setDestinationLocation(destinationLocation);
		newRobot.setMainController(mainController);
		newRobot.setSpeed(speed);
		newRobot.setProgress(progress);
		newRobot.setMovingDirection(movingDirection);
		
		return newRobot;
	}
	
	//Mutators
	public void setMainController(MainController inMainController)
	{
		mainController = inMainController;
	}
	
	public void setSpeed(double inSpeed)
	{
		speed = inSpeed;
	}
	
	public void setProgress(double inProgress)
	{
		progress = inProgress;
	}
	
	public void increaseProgression()
	{
		progress += speed;
	}
	
	public void setCurrentLocation(Location<Integer> inLocation)
	{
		currentLocation = inLocation;
	}
	
	public void setDestinationLocation(Location<Integer> inLocation)
	{
		destinationLocation = inLocation;
	}
	
	public void setMovingDirection(String inDirection)
	{
		movingDirection = inDirection;
	}
	
	public void resetDestinationLocation()
	{
		destinationLocation = null;
	}
    
	public void resetProgress()
	{
		progress = 0.0;
	}
	
    //Accessors
    public int getUniqueID()
    {
        return uniqueID;
    }
    
    public int getDelay()
    {
        return delay;
    }
	
	public double getSpeed()
	{
		return speed;
	}
	
	public double getProgress()
	{
		return progress;
	}
	
	public Location<Integer> getCurrentLocation()
	{
		return currentLocation;
	}
	
	public Location<Integer> getDestinationLocation()
	{
		return destinationLocation;
	}
	
	public String getMovingDirection()
	{
		return movingDirection;
	}
	
	public void endLife()
	{
		//End thread inside here.
	}
    
    public String nameToString()
    {
        return "Robot: " + uniqueID;
    }
	
	public void cleanUp()
	{
		if (destinationLocation != null)
		{
			destinationLocation = null;
		}
		
		if (!movingDirection.equals("Neutral"))
		{
			movingDirection = "Neutral";
		}
		
		/*if ((1.0 - progress) > TOLERANCE)
		{
			progress = 0.0;
		}*/
		progress = 0.0;
	}
}