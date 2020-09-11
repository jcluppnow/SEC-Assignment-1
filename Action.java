public class Action
{
	private Location<Integer> coordinates;
	private long timeCreated;
	
	public Action(Location<Integer> inCoordinates, long inTimeCreated)
	{
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
}