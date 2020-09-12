import java.util.*;
import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class MainController
{
	private Object mutex = new Object();
	
    private ArrayList<Robot> robotsOnBoard;
	private ArrayList<RobotController> robotControllers;
	
    private SwingArena arena;
	private MapData mapData;
	private ScoreTracker scoreTracker;
	
	private JTextArea logger;
	private JLabel scoreLabel;
	private ExecutorService fixedThreadPool;
	
    //This Controller will get the Robots from Factory.
    //Will control all communication Between Robots and Arena
    public MainController(MapData inMapData) throws MainControllerException
	{
		if (inMapData == null)
		{
			throw new MainControllerException("Map Data is null - Inside Main Controller Constructor.");
		}
		else
		{
			mapData = inMapData;
			robotsOnBoard = new ArrayList<Robot>();
			robotControllers = new ArrayList<RobotController>();
			fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		}
	}
	
    public MainController(SwingArena inArena, MapData inMapData) throws MainControllerException
    {
        if (inArena == null || inMapData == null)
        {
            if (arena == null)
			{
				throw new MainControllerException("Arena is null - Inside Main Controller Constructor.");
			}
			else
			{
				throw new MainControllerException("Map Data is null - Inside Main Controller Constructor.");
			}
        }
		else if ((inArena == null) && (inMapData == null))
		{
			throw new MainControllerException("Arena & Map Data is null - Inside Main Controller Constructor.");
		}
        else
        {
            arena = inArena;
			mapData = inMapData;
            robotsOnBoard = new ArrayList<Robot>();
        }
    }
    
	public boolean startTaskInNewThread(Runnable inTask)
	{
		boolean startTask = false;
		
		try
		{
			fixedThreadPool.submit(inTask);
			startTask = true;
		}
		catch (RejectedExecutionException rejectedException)
		{
			//This occurs when the task cannot be scheduled for execution.
		}
		catch (NullPointerException nullPointer)
		{
			//Occurs if the Task submitted is null.
		}
		
		return startTask;
	}
	
    public ArrayList<Robot> getRobotsList()
    {
		ArrayList<Robot> clonedList = new ArrayList<Robot>();
		synchronized(mutex)
		{

			//Loop through each element and clone it.
			for (Robot tempRobot : robotsOnBoard)
			{
				clonedList.add(tempRobot.clone());
			}
		}
        return clonedList;
    }
    
	//Might have to synchronise this whole method.
    public ArrayList<Location<Double>> getNewLocations()
    {
        ArrayList<Location<Double>> newLocations = new ArrayList<Location<Double>>();
        //ArrayList<Robot> clonedList = new ArrayList<Robot>();
		synchronized(mutex)
		{
				
				/*//Loop through each element and clone it.
				for (Robot tempRobot : robotsOnBoard)
				{
					clonedList.add(tempRobot.clone());
				}*/
			
			//Synchronized and make a copy of newLocations
			//Which we will iterate over instead.
			
			//for (Robot tempRobot : clonedList)
			for (Robot tempRobot : robotsOnBoard)
			{
				Location<Double> newLocation = new Location<Double>();
				Location<Integer> currentLocation = tempRobot.getCurrentLocation();
				Location<Integer> destinationLocation = tempRobot.getDestinationLocation();            
				
				//If the Destination Location is null, the Robot hasn't moved.
				/*if (destinationLocation == null)
				{
					//Paint the Robot in it's currentLocation
					//Typecast to double.
					newLocation.setX(Double.valueOf(currentLocation.getX()));
					newLocation.setY(Double.valueOf(currentLocation.getY()));
					newLocation.setLocationName(tempRobot.nameToString());
					//Once all the Values of the Robot have been set to the new Location.
					//Add it to the New Locations List.
					newLocations.add(newLocation);
				}
				else
				{
					//If the destinationLocation isn't null, the Robot is moving.
					newLocation = getPositionAlongRoute(tempRobot);
					
					newLocations.add(newLocation);
				}*/
				
				//If the Moving Direction is Neutral, the Robot isn't moving so just
				//Display at the Robot's current location.
				if (tempRobot.getMovingDirection().equals("Neutral"))
				{
					//Paint the Robot in it's currentLocation
					//Typecast to double.
					newLocation.setX(Double.valueOf(currentLocation.getX()));
					newLocation.setY(Double.valueOf(currentLocation.getY()));
					newLocation.setLocationName(tempRobot.nameToString());
					
					//System.out.println("Neutral - getNewLocations() debug: " + newLocation.locationToString());
					//Once all the Values of the Robot have been set to the new Location.
					//Add it to the New Locations List.
					newLocations.add(newLocation);
				}
				else
				{
					//If the destinationLocation isn't null, the Robot is moving.
					newLocation = getPositionAlongRoute(tempRobot);
					
					//System.out.println("Moving - getNewLocations() debug: " + newLocation.locationToString() + tempRobot.toString());
					
					newLocations.add(newLocation);
				}
			}
		}
		
		return newLocations;
    }
	
    public void robotHasMoved()
    {
        //This Method updates the Arena
        //Want this to happen on Robots thread.
		synchronized(mutex)
		{
			SwingUtilities.invokeLater(() ->
			{
				//Do I want this happening on Arena thread?
				arena.animateRobots(getNewLocations());
			});
		}
	}
    
    public Robot removeRobot(int x, int y)
    {
		System.out.println("X: " + x + " Y: " + y + " Reached inside Remove Robot at: " + System.currentTimeMillis());
		
		System.out.println("Size: " + robotsOnBoard.size());
		Robot clonedRobot = null;
		
		//Task will check if the Robot is on the Map, remove it and the update ScoreTracker with Score.
		synchronized(mutex)
		{
			System.out.println("X: " + x + " Y: " + y + " Reached inside synchronized statement at: " + System.currentTimeMillis());
			Robot robotToBeRemoved = null;
			
			if (robotsOnBoard.isEmpty())
			{
				System.out.println("Robots on Board shouldn't be empty.");
			}
			
			for (Robot tempRobot : robotsOnBoard)
			{
				//Check if Current Location or Destination Location equals the
				//Clicked Coordinates, if so remove it.
				//Do I need a read only version?
				Location<Integer> currentLocation = tempRobot.getCurrentLocation();
				Location<Integer> destinationLocation = tempRobot.getDestinationLocation();
				
				System.out.println("Remove Robot Debug: Target: (" + x + ", " + y + ") Current Location: " + currentLocation.locationToString() + "Destination Location: " + destinationLocation.locationToString());
				if (currentLocation != null)
				{
					if ((currentLocation.getX() == x) && (currentLocation.getY() == y))
					{
						robotToBeRemoved = tempRobot;
					}
					
					if (destinationLocation != null)
					{
						if ((destinationLocation.getX() == x) && (currentLocation.getY() == y))
						{
							//Clean up Robot ends thread and Removes the Robot from the Board.
							robotToBeRemoved = tempRobot;
						}
					}
				}
				else
				{
				   //Handle error
				}
			}
			
			System.out.println("Skipping For Loop inside MainController - RemoveRobot");
			if (robotToBeRemoved == null)
			{
				clonedRobot = cleanUpRobot(robotToBeRemoved);
			}
		}
		return clonedRobot;
    }
	
	public void addRobotController(RobotController inRobotController) 
	{
		//Shouldn't need to synchronise this as only the Factory Controller thread
		//can add to this list.
		//No need to check if inRobotController is null as this is done
		//where the Robot Controller is created (FactoryController).
		robotControllers.add(inRobotController);
	}
		
	private Robot cleanUpRobot(Robot inRobot)
	{
		Robot newRobot = inRobot.clone();
		System.out.println("Number of Robots on Board before Removal: " + robotsOnBoard.size());
		robotsOnBoard.remove(inRobot);
		System.out.println("Number of Robots on Board after Removal: " + robotsOnBoard.size());
		RobotController controllerToBeRemoved = null;
		
		System.out.println("Number of Controllers before Removal: " + robotControllers.size());
		//Iterate through controllers and find match.
		for (RobotController robotController : robotControllers)
		{
			if (robotController.matchingRobot(inRobot) == true)
			{
				//Call the Stop Thread inside the Controller which will then handle it's own cleanup
				robotController.stopThread();
				controllerToBeRemoved = robotController;
			}
		}
		
		if (controllerToBeRemoved != null)
		{
			robotControllers.remove(controllerToBeRemoved);
			System.out.println("Number of Controllers after Removal: " + robotControllers.size());
		}
		
		//Return the Robot we cloned.
		return newRobot;		
	}
	
	public boolean setNewRobotInCorner(Robot inRobot)
	{
		boolean setStatus = mapData.setCorner(inRobot);
		
		if (setStatus == true)
		{
			synchronized(mutex)
			{
				robotsOnBoard.add(inRobot);
				
			}
			//Robot has successfully been created so we want to add this event to the logger.
			printEvent("Robot " + inRobot.getUniqueID() + ": Created at " + inRobot.getCurrentLocation().locationToString());
			//Since a new Robot has been added, the Map has changed.
			//So we can tell SwingArena to repaint.
			robotHasMoved();
		}
		
		return setStatus;
	}
  
	public boolean setMovingRobotLocation(Robot inRobot)
	{
		return mapData.setRobot(inRobot);
	}
	
	public String getDirection(Robot inRobot)
	{
		return mapData.getDirection(inRobot.getCurrentLocation(), inRobot.getDestinationLocation());
	}
	
	public void clearRobot(Location<Integer> inLocation)
	{
		mapData.clearRobot(inLocation);
	}
	private Location<Double> getPositionAlongRoute(Robot inRobot)
	{
		Location<Double> positionOnRoute = new Location<Double>(-1.0, -1.0, "Poison");
		Location<Integer> currentLocation = inRobot.getCurrentLocation();
		String direction = inRobot.getMovingDirection();
		double y, x;
		
		switch (direction)
		{
			case "Up":
				//x will stay the same and y will decrease.
				//Just need to calculate new y position.
				//Imagine 2,1 moving towards 2,0.
				x = currentLocation.getX(); //Convert to Double?
				y = currentLocation.getY() - inRobot.getProgress();
				break;
			
			case "Down":
				//x will stay the same and y will increase.
				//Just need to calculate new y position.
				//Imagine 2,1 moving towards 2,2.
				x = currentLocation.getX();
				y = currentLocation.getY() + inRobot.getProgress();
				break;
			
			case "Left":
				//y will stay the same and x will decrease.
				//Just need to calculate new x position.
				//Imagine 2,1 moving towards 1,1.
				x = currentLocation.getX() - inRobot.getProgress();
				y = currentLocation.getY();
				break;
	
			case "Right":
				//y will stay the same and x will increase.
				//Just need to calculate new x position.
				//Imagine 2,1 moving towards 3,1.
				x = currentLocation.getX() + inRobot.getProgress();
				y = currentLocation.getY();
				break;

			default:
				//Do some error handling.
				//Change this later or else it will say the drawLabel below
				//x,y is possibly unitialized
				throw new IllegalArgumentException();
		}
		
		positionOnRoute.setX(x);
		positionOnRoute.setY(y);
		positionOnRoute.setLocationName(inRobot.nameToString());
		
		//System.out.println("Position: " + positionOnRoute.locationToString() + "Robot: " + inRobot.toString());
		return positionOnRoute;
	}
	
	public void setArena(SwingArena inArena)
	{
		arena = inArena;
	}
	
	public void setScoreLabel(JLabel inScoreLabel)
	{
		scoreLabel = inScoreLabel;
	}
	
	public void setLogger(JTextArea inLogger)
	{
		logger = inLogger;
	}
	
	public void setScoreTracker(ScoreTracker inScoreTracker)
	{
		scoreTracker = inScoreTracker;
	}
	
	//Do we need to synchronise this as multiple threads will be calling this.
	public void printEvent(String inEvent)
	{
		System.out.println("Attempting to print event: \n" + inEvent);
		SwingUtilities.invokeLater(() ->
        {
			logger.append(inEvent + "\n");
		});
	}
	
	public void updateScore(int inScore)
	{
		SwingUtilities.invokeLater(() ->
		{
			scoreLabel.setText("Score: " + inScore);
		});
	}	
	
	public void notifyScoreTrackerDestroyed(Robot inRobot, long inTimeTaken)
	{
		Runnable notifyScoreTask = () ->
		{
			//Do some work on the new thread.
			scoreTracker.destroyedRobot(inRobot, inTimeTaken);
		};
		
		//Submit this task to the thread pool.
		startTaskInNewThread(notifyScoreTask);
	}
}