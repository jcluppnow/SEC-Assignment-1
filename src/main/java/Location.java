public class Location<T>
{
	private T x, y;
	private String locationName;
    
	public Location()
	{
		locationName = "";
	}
	
	public Location(T inX, T inY, String inName)
	{
		x = inX;
		y = inY;
		locationName = inName;
	}
	
	public T getX()
	{
		return x;
	}
	
	public T getY()
	{
		return y;
	}
	
	public String getLocationName()
	{
		return locationName;
	}
    
    public void setX(T inX)
    {
        x = inX;
    }
    
    public void setY(T inY)
    {
        y = inY;
    }
    
    public void setLocationName(String inName)
    {
        locationName = inName;
    }
	
	public String locationToString()
	{
		return "(" + x + ", " + y + ")";
    }
}