
public class FactoryController implements Runnable
{
	private MainController mainController;
	private RobotFactory robotFactory;
	private int creationDelay;
	
	private Thread myThread;
	
	public FactoryController(MainController inMainController, RobotFactory inRobotFactory, int inCreationDelay)
	{
		if (inMainController == null || inRobotFactory == null)
		{
			//Throw FactoryController exception
			System.out.println("\n\nINSIDE FACTORYCONTROLLER CONSTRUCTOR:\nFactoryController not being constructed properly");
			if (mainController == null)
			{
				System.out.println("\tMain Controller is Null.");
			}
			
			if (inRobotFactory == null)
			{
				System.out.println("\tRobot Factory is Null.\n\n");
			}
		}
		else
		{	
			mainController = inMainController;
			robotFactory = inRobotFactory;
			creationDelay = inCreationDelay;
		}
	}
	
	public void run()
	{
		try
		{
			while (true)
			{
				if (robotFactory == null)
				{
					System.out.println("\n\nRobot Factory is null\n\n");
				}
				
				//Every Creational Delay - Try to create another Robot.
				Robot newRobot = robotFactory.createRobot();
				RobotController newRobotController = robotFactory.createRobotController(mainController, newRobot);
					
				//Check if they were initialized correctly.
				if ((newRobot != null) && (newRobotController != null))
				{
					if (mainController.setNewRobotInCorner(newRobot) == true)
					{
						//Start up the movement thread.
						newRobotController.start();
					}
					else
					{
						//Reset factory details by one.
						robotFactory.decrement();
						//If I get the chance, rather than restart, wait and try again to 
						//set corner again after Creation Delay. Don't waste resources.
					}
				}
				myThread.sleep(creationDelay);
			}
		}
		catch (InterruptedException interruptedException)
		{
			//Do something here.
		}
	}
}