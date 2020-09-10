import java.util.*;

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
        
        randomNumber = rand.nextInt(2000 - 500) + 500;
        return randomNumber;
    }
	
	public void decrement()
	{
		id--;
	}
}