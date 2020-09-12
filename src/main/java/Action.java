public class Action
{
	private Location<Integer> coordinates;
	private long timeCreated;
	
	public Action(Location<Integer> inCoordinates, long inTimeCreated) throws ActionException
	{
		if (inCoordinates == null)
		{
			throw new ActionException("Coordinates is null - Action Constructor");
		}
		
		coordinates = inCoordinates;
		timeCreated = inTimeCreated;
	}
	
	//Accessors
	public int getXCoordinate()
	{
		return coordinates.getX();
	}
	
	public int getYCoordinate()
	{
		return coordinates.getY();
	}
	
	public long getTimeCreated()
	{
		return timeCreated;
	}
	
	public String getLocationString()
	{
		return coordinates.locationToString();
	}
	
	public String toString()
	{
		return "Coordinates: " + coordinates.locationToString() + " Time Created: " + timeCreated;
	}
}