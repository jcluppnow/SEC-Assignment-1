import java.util.*;

public class MapData
{
    private Object mutex = new Object();
    private Robot[] map;
    private int width, height;
    private Random rand;
	private int topLeftCornerIndex, topRightCornerIndex, bottomLeftCornerIndex, bottomRightCornerIndex;
    private Location<Integer> topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner;

	
	public MapData(int inWidth, int inHeight)
    {
        width = inWidth;
        height = inHeight;
		map = new Robot[inWidth * inHeight];
		rand = new Random();
		
		//Compute corners
		//Sacrifice some memory to have both the indexes and locations calculated
		//Once at the start so that it doesn't need to be calculated everytime a Robot wants to
		//Be created.
		topLeftCornerIndex = getIndexFromCoordinates(0, 0);
		topRightCornerIndex = getIndexFromCoordinates(width - 1, 0);
		bottomLeftCornerIndex = getIndexFromCoordinates(0, height - 1);
		bottomRightCornerIndex = getIndexFromCoordinates(width - 1, height - 1);
		
		topLeftCorner = new Location<Integer>(0,0, "Top Left Corner");
		topRightCorner = new Location<Integer>(width - 1, 0, "Top Right Corner");
		bottomLeftCorner = new Location<Integer>(0, height - 1, "Bottom Left Corner");
		bottomRightCorner = new Location<Integer>(width - 1, height - 1, "Bottom Right Corner");
		
	}
    
    public int getIndexFromCoordinates(int x, int y)
    {
		//Error handling needed
        //Mapping index from a 2D Coordinate
		int newIndex = y * width + x;
		
		if(withinBounds(x, y) == false)
		{
			//Throw a OutOfBounds exception.
		}

		return newIndex;
    }
    
    public Location getCoordinatesFromIndex(int inIndex)
    {
		//Error handling needed
        int y = inIndex / width;
        int x = inIndex % width;
        
        return new Location<Integer>(x, y, "Coordinates");
    }
    
	public boolean setCorner(Robot inRobot)
	{
		boolean success = false;
		
		int designatedIndex = -1;
		
		synchronized(mutex)
		{
			Location<Integer> availableLocation = getRandomAvailableCorner();
			int availableLocationX = availableLocation.getX();
			int availableLocationY = availableLocation.getY();
			
			if ((availableLocationX != -1) && (availableLocationY != -1))
			{
				designatedIndex = getIndexFromCoordinates(availableLocationX, availableLocationY);
				
				map[designatedIndex] = inRobot;
				//System.out.println("Setting Corner to: " + availableLocation.locationToString());
				inRobot.setCurrentLocation(availableLocation);
				success = true;
			}
		}
		
		return success;
	}
	
    public boolean setRobot(Robot inRobot)
    {
        boolean success = false;
		
        //Get the mapping.
        int designatedIndex = -1;
        
        synchronized(mutex)
        {
			Location<Integer> availableLocation = getRandomAvailableLocation(inRobot);
			//System.out.println("Setting Robot: " + inRobot.getUniqueID() + "Current Location: " + inRobot.getCurrentLocation().locationToString() + availableLocation.locationToString());
			int availableLocationX = availableLocation.getX();
			int availableLocationY = availableLocation.getY();
			
			//Catch Array out of bounds
			if ((availableLocationX != -1) && (availableLocationY != -1))
			{
				designatedIndex = getIndexFromCoordinates(availableLocationX, availableLocationY);
				
				//System.out.println("Setting Location for: " + inRobot.toString() + "L:" + availableLocation.locationToString());
				map[designatedIndex] = inRobot;
				inRobot.setDestinationLocation(availableLocation);
				success = true;
			}
			else
			{
				success = false;
			}
        }
		
		if (success == false)
		{
			//System.out.println("Didn't set Robot: " + inRobot.getUniqueID() + " to Location");
		}
        
        return success;
    }   

	public boolean clearRobot(Location<Integer> inLocation)
	{
		boolean clearStatus = false;
		int x = inLocation.getX();
		int y = inLocation.getY();
		
		if (withinBounds(x, y) == true)
		{
			synchronized(mutex)
			{
				int robotToBeRemoved = getIndexFromCoordinates(x, y);
				map[robotToBeRemoved] = null;
				if (map[robotToBeRemoved] == null)
				{
					clearStatus = true;
				}
			}
		}
		
		return clearStatus;
	}   
	
	public Location<Integer> getRandomAvailableLocation(Robot inRobot)
	{
		ArrayList<Location<Integer>> availableLocations = new ArrayList<Location<Integer>>();
		Location<Integer> chosenLocation = new Location<Integer>(-1, -1, "Poison Location");
		Location<Integer> currentLocation = inRobot.getCurrentLocation();
		
		//First location - Up. Imagine 2,1 travelling to 2,0.
		int upX = currentLocation.getX();
		int upY = currentLocation.getY() - 1;
		int upIndex = getIndexFromCoordinates(upX, upY);
		
		//Second location - Down. Imagine 2,1 travelling to 2,2.
		int downX = currentLocation.getX();
		int downY = currentLocation.getY() + 1;
		int downIndex = getIndexFromCoordinates(downX, downY);
		
		//Third location - Left. Imagine 2,1 travelling to 1,1.
		int leftX = currentLocation.getX() - 1;
		int leftY = currentLocation.getY();
		int leftIndex = getIndexFromCoordinates(leftX, leftY);
		
		//Fourth location - Right. Imagine 2,1 travelling to 3,1.
		int rightX = currentLocation.getX() + 1;
		int rightY = currentLocation.getY();
		int rightIndex = getIndexFromCoordinates(rightX, rightY);
		
		if (withinBounds(upX, upY) == true)
		{
			if (map[upIndex] == null)
			{
				availableLocations.add(new Location<Integer>(upX, upY, "New Up Location"));
			}
		}
		
		if (withinBounds(downX, downY) == true)
		{
			if (map[downIndex] == null)
			{
				availableLocations.add(new Location<Integer>(downX, downY, "New Down Location"));
			}
		}
		
		if (withinBounds(leftX, leftY) == true)
		{
			if (map[leftIndex] == null)
			{
				availableLocations.add(new Location<Integer>(leftX, leftY, "New Left Location"));
			}
		}
		
		if (withinBounds(rightX, rightY) == true)
		{
			if (map[rightIndex] == null)
			{
				availableLocations.add(new Location<Integer>(rightX, rightY, "New Right Location"));
			}
		}
		
		//If there are no available locations, -1 will be returned as an error index.
		if (!availableLocations.isEmpty())
		{
			//Generates a random integer from 0 (inclusive) to bound (exclusive).
			//Gets a Random Integer between 0 and size (exclusive) for size.
			//Retrieves the location at this Random Integer.
			chosenLocation = availableLocations.get(rand.nextInt(availableLocations.size()));
			//System.out.println("Robot: " + inRobot.getUniqueID() +"\n\tChosen Location: " + chosenLocation.getX() + "," + chosenLocation.getY());
		}
		
		return chosenLocation;
		
	}
	
	private Location<Integer> getRandomAvailableCorner()
	{
		ArrayList<Location<Integer>> availableLocations = new ArrayList<Location<Integer>>();
		Location<Integer> chosenLocation = new Location<Integer>(-1, -1, "Poison Location");
		
		if(map[topLeftCornerIndex] == null)
		{
			availableLocations.add(topLeftCorner);
		}
		
		if (map[topRightCornerIndex] == null)
		{
			availableLocations.add(topRightCorner);
		}
		
		if (map[bottomLeftCornerIndex] == null)
		{
			availableLocations.add(bottomLeftCorner);
		}
		
		if (map[bottomRightCornerIndex] == null)
		{
			availableLocations.add(bottomRightCorner);
		}

		if (!availableLocations.isEmpty())
		{
			//Generates a random integer from 0 (inclusive) to bound (exclusive).
			//Gets a Random Integer between 0 and size (exclusive) for size.
			//Retrieves the location at this Random Integer.
			chosenLocation = availableLocations.get(rand.nextInt(availableLocations.size()));
		}
		
		return chosenLocation;
	}
	
	public String getDirection(Location<Integer> currentLocation, Location<Integer> destinationLocation)
	{
		String direction = "";
		
		int currentXLocation = currentLocation.getX();
		int currentYLocation = currentLocation.getY();
		
		int destinationXLocation = destinationLocation.getX();
		int destinationYLocation = destinationLocation.getY();
		
		//If y is equal, vertical movement is happening and only x changes.
		//If x is equal, horizontal movement is happening and only y changes.
		//Need to handle for out of bounds array
		
		if (currentXLocation == destinationXLocation)
		{
			if (currentYLocation == (destinationYLocation + 1))
			{
				//This is moving upwards.
				//Imagine 2,1 moving to 2,0.
				direction = "Up";
			}
			else
			{
				//If currentYLocation == destinationYLocation - 1.
				//This is moving downwards.
				//Imagine 2,1 moving to 2,2.
				direction = "Down";
			}
		}
		else if (currentYLocation == destinationYLocation)
		{
			if (currentXLocation == (destinationXLocation + 1))
			{
				//This is moving leftwards.
				//Imagine 2,1 moving to 1,1
				direction = "Left";
			}
			else
			{
				//If currentYLocation == destinationLocation - 1.
				//This is moving rightwards.
				//Imagine 2,1 moving to 3,1.
				direction = "Right";
			}
		}
		else
		{
			//Something has gone wrong, throw an exception.
			System.out.println("\n\tCurrent: " + currentLocation.getLocationName() + " (" + currentXLocation + ", " + currentYLocation + ")");
			System.out.println("\tDestination: " + currentLocation.getLocationName() + " (" + destinationXLocation + ", " + destinationYLocation + ")");
			throw new IllegalArgumentException("Problem");
			
		}
		//System.out.println("Direction from (" + currentXLocation + ", " + currentYLocation + ") -> (" + destinationXLocation + ", " + destinationYLocation + "): " + direction);
		return direction;
	}
	
	private boolean withinBounds(int inX, int inY)
	{
		boolean withinBounds = true;
		
        if (inX < 0 || inX >= width)
        {
            withinBounds = false;
            //System.out.println("X is incorrect: " + inX + "Width: " + width);
        }
        
        if (inY < 0 || inY >= height)
        {
            withinBounds = false;
            //System.out.println("Y is incorrect: " + inY + "Height: " + height);
        }

		return withinBounds;
	}
}  