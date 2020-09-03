public class GameMap
{
    private StoredRobot[][] map;
    private int width, height;
    private Location fortress, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner;
	
    public GameMap(int inRowSize, int inColumnSize)
    {
        map = new StoredRobot[inRowSize][inColumnSize];
        width = inRowSize;
        height = inColumnSize;
		fortress = new Location(height/2, width/2, "Fortress");
		topLeftCorner = new Location(0,0, "Top Left Corner");
		topRightCorner = new Location(height-1, 0, "Top Right Corner");
		bottomLeftCorner = new Location(0, width-1, "Bottom Left Corner");
		bottomRightCorner = new Location(height-1, width-1, "Bottom Right Corner");
		
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                map[i][j] = new StoredRobot();
            }
        }
    }
    
    public void printMap()
    {
		System.out.println("\nMap Layout:\n");
		for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
				if (map[i][j] == null)
                {
                    System.out.print("\u001b[31mNull  \u001b[0m");
                }
                else
                {
                    System.out.print("\u001b[32mNot Null  \u001b[0m");
                }
            }
			System.out.println();
        }
    }	
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Location getFortress()
	{
		return fortress;
	}
	
	public Location getTopLeftCorner()
	{
		return topLeftCorner;
	}
	
	public Location getTopRightCorner()
	{
		return topRightCorner;
	}
	
	public Location getBottomLeftCorner()
	{
		return bottomLeftCorner;
	}
	
	public Location getBottomRightCorner()
	{
		return bottomRightCorner;
	}
	
	public int getFortressX()
	{
		return fortress.getRow();
	}
	
	public int getFortressY()
	{
		return fortress.getColumn();
	}
}