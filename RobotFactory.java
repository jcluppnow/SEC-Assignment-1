import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RobotFactory
{
	private int id;
	private Random rand;
	
	public RobotFactory()
	{
		id = 1;
		rand = new Random();
	}
	
	public Robot createRobot()
	{
		Robot newRobot = new Robot(id, getRandomDelay());
		id++;
		
		return newRobot;
	}
	
	public RobotController createRobotController(MainController inMainController, Robot inRobot)
	{
		return new RobotController(inMainController, inRobot);
	}
		
	private int getRandomDelay()
	{
        int randomNumber = -1;
        Random rand = new Random();
        
		//ThreadLocalRandom - has less overhead to Random class.
		//Takes 2 values lower boundary (inclusive) and upper boundary (exclusive).
		//Made the assumption that the delay was inclusive for 2000.
        randomNumber = ThreadLocalRandom.current().nextInt(500, 2001);
		
        return randomNumber;
    }
	
	public void decrement()
	{
		id--;
	}
}