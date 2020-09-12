
public class FactoryController implements Runnable
{
	private MainController mainController;
	private RobotFactory robotFactory;
	private int creationDelay;
	
	public FactoryController(MainController inMainController, RobotFactory inRobotFactory, int inCreationDelay) throws FactoryControllerException
	{
		if (inMainController == null || inRobotFactory == null)
		{
			//Throw FactoryController exception
			if (mainController == null)
			{
				throw new FactoryControllerException("Main Controller is null - Inside FactoryController Constructor.");
			}
			
			if (inRobotFactory == null)
			{
				throw new FactoryControllerException("Main Controller is null - Inside FactoryController Constructor.");
			}
		}
		else if ((inMainController == null) && (inRobotFactory == null))
		{
			throw new FactoryControllerException("Main Controller is null - Inside FactoryController Constructor.");
		}
		else
		{	
			mainController = inMainController;
			robotFactory = inRobotFactory;
			creationDelay = inCreationDelay;
		}
	}
	
	//Task that runs on this Thread
	@Override
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
						//Add all the Robot Controllers to a List in MainController so that
						//its easy to just iterate through and find the Robots controller.
						mainController.addRobotController(newRobotController);
						
						//Start up the movement thread.
						//We start this by submitting the robotController as a Task to the ThreadPool.
						mainController.startTaskInNewThread(newRobotController);
					}
					else
					{
						//Reset factory details by one.
						robotFactory.decrement();
						//If I get the chance, rather than restart, wait and try again to 
						//set corner again after Creation Delay. Don't waste resources.
					}
				}
				Thread.sleep(creationDelay);
			}
		}
		catch (InterruptedException interruptedException)
		{
			//Do something here.
		}
		catch (RobotFactoryException robotFactoryException)
		{
			System.out.println(robotFactoryException.getMessage());;
		}
	}
}