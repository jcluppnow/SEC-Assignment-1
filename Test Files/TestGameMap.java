public class TestGameMap
{
	public static void main(String[] args)
	{
		GameMap gameMapEven, gameMapOdd, gameMapEvenOdd, gameMapOddEven;
		
		gameMapEven = new GameMap(6,6);
		
		gameMapOdd = new GameMap(5,5);
		
		gameMapEvenOdd = new GameMap(4,5);
		
		gameMapOddEven = new GameMap(5,4);
		
		testGameMapEven(gameMapEven);
		
		testGameMapOdd(gameMapOdd);
		
		testGameMapEvenOdd(gameMapEvenOdd);
		
		testGameMapOddEven(gameMapOddEven);
		
		System.out.println();
	}
	
	private static void testGameMapEven(GameMap inGameMap)
	{
		System.out.println("\n\u001b[34mTesting Game Map Even - 6x6\u001b[0m");
		Location fortress, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner;
		
		fortress = inGameMap.getFortress();
		topLeftCorner = inGameMap.getTopLeftCorner();
		topRightCorner = inGameMap.getTopRightCorner();
		bottomLeftCorner = inGameMap.getBottomLeftCorner();
		bottomRightCorner = inGameMap.getBottomRightCorner();
		
		testLocation(fortress, 3, 3);
		testLocation(topLeftCorner, 0, 0);
		testLocation(topRightCorner, 5, 0);
		testLocation(bottomLeftCorner, 0, 5);
		testLocation(bottomRightCorner, 5, 5);
		

		inGameMap.printMap();
		
	}
	
	private static void testGameMapOdd(GameMap inGameMap)
	{
		System.out.println("\n\u001b[34mTesting Game Map Odd - 5x5\u001b[0m");
		Location fortress, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner;
		
		fortress = inGameMap.getFortress();
		topLeftCorner = inGameMap.getTopLeftCorner();
		topRightCorner = inGameMap.getTopRightCorner();
		bottomLeftCorner = inGameMap.getBottomLeftCorner();
		bottomRightCorner = inGameMap.getBottomRightCorner();
		
		testLocation(fortress, 2, 2);
		testLocation(topLeftCorner, 0, 0);
		testLocation(topRightCorner, 4, 0);
		testLocation(bottomLeftCorner, 0, 4);
		testLocation(bottomRightCorner, 4, 4);
		
		
		inGameMap.printMap();
	}
	
	private static void testGameMapEvenOdd(GameMap inGameMap)
	{
		System.out.println("\n\u001b[34mTesting Game Map Even Odd - 4x5\u001b[0m");
		Location fortress, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner;
		
		fortress = inGameMap.getFortress();
		topLeftCorner = inGameMap.getTopLeftCorner();
		topRightCorner = inGameMap.getTopRightCorner();
		bottomLeftCorner = inGameMap.getBottomLeftCorner();
		bottomRightCorner = inGameMap.getBottomRightCorner();
		
		testLocation(fortress, 2, 2);
		testLocation(topLeftCorner, 0, 0);
		testLocation(topRightCorner, 4, 0);
		testLocation(bottomLeftCorner, 0, 3);
		testLocation(bottomRightCorner, 4, 3);
		
		
		inGameMap.printMap();
	}
	
	private static void testGameMapOddEven(GameMap inGameMap)
	{
		System.out.println("\n\u001b[34mTesting Game Map Odd Even - 5x4\u001b[0m");
		Location fortress, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner;
		
		fortress = inGameMap.getFortress();
		topLeftCorner = inGameMap.getTopLeftCorner();
		topRightCorner = inGameMap.getTopRightCorner();
		bottomLeftCorner = inGameMap.getBottomLeftCorner();
		bottomRightCorner = inGameMap.getBottomRightCorner();
		
		testLocation(fortress, 2, 2);
		testLocation(topLeftCorner, 0, 0);
		testLocation(topRightCorner, 3, 0);
		testLocation(bottomLeftCorner, 0, 4);
		testLocation(bottomRightCorner, 3, 4);
		
		
		inGameMap.printMap();
	}
	
	private static void testLocation(Location inLocation, int expectedRow, int expectedColumn)
	{
		String message = "\n";
		if ((inLocation.getRow() == expectedRow) && (inLocation.getColumn() == expectedColumn))
		{
			message += "\u001b[32mCorrect\u001b[0m Location for " + inLocation.getLocationName();
			message += "\n\tExpected Row: " + expectedRow + " Actual Row: " + inLocation.getRow();
			message += "\n\tExpected Column: " + expectedColumn + " Actual Column: " + inLocation.getColumn();
		}
		else
		{
			message += "\u001b[31mIncorrect\u001b[0m Location for " + inLocation.getLocationName();
			if (inLocation.getRow() != expectedRow)
			{
				message += " Row\n\tExpected Row: " + expectedRow + " Actual Row: " + inLocation.getRow();
			}
			else
			{
				message += " Column\n\tExpected Column: " + expectedColumn + " Actual Column: " + inLocation.getColumn();
			}
		}
		
		System.out.println(message);
	}
}