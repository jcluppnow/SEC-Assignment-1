public class Action
{
	private int x, y;
	private long timeCreated;
	
	public Action(int inX, int inY, long inTimeCreated)
	{
		x = inX;
		y = inY;
		timeCreated = inTimeCreated;
	}
	
	//Accessors
	public int getXCoordinate()
	{
		return x;
	}
	
	public int getYCoordinate()
	{
		return y;
	}
	
	public long getTimeCreated()
	{
		return timeCreated;
	}
}