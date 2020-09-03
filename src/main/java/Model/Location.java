public class Location
{
	private int row, column;
	private String locationName;
	
	public Location(int inRow, int inColumn, String inName)
	{
		row = inRow;
		column = inColumn;
		locationName = inName;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public String getLocationName()
	{
		return locationName;
	}
}