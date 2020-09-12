//Model Class
public class ActionProcessor implements Runnable
{
	private MainController mainController;
	private	ActionsContainer actionsContainer;
	
	public ActionProcessor(MainController inMainController, ActionsContainer inActionsContainer) throws ActionProcessorException
	{
		if ((inMainController == null) || (inActionsContainer == null))
		{
			if (inMainController == null)
			{
				throw new ActionProcessorException("Main Controller is null - Inside Action Processor Constructor.");
			}
			else
			{
				throw new ActionProcessorException("Actions Container is null - Inside Action Processor Constructor.");
			}
		}
		else if ((inMainController == null) && (inActionsContainer == null))
		{
			throw new ActionProcessorException("Main Controller & Actions Container is null - Inside Action Processor Constructor.");
		}
		else
		{
			mainController = inMainController;
			actionsContainer = inActionsContainer;
		}
	}
	
	//This is the long running task that will run in the Thread.
	@Override
	public void run()
	{
		System.out.println("Action Processor Thread Started.");
		try
		{
			while (true)
			{
				
				//Obtain the next Action (might throw InterruptedException)
				Action nextAction  = actionsContainer.getNextAction();
				
				System.out.println("Processing Action: \n" + nextAction.toString());
				
				//Get the current time, which will be compared to the time that the action is created. 
				//Might move this to where it checks the Robot.
				long timeProcessed = System.currentTimeMillis();
				
				//Call removeRobot inside MainController which will hit or miss
				//Depending on whether there is a Robot in this spot.
				Robot removedRobot = mainController.removeRobot(nextAction.getXCoordinate(), nextAction.getYCoordinate());
				
				//Robot will be null if there was no Robot at the spot.
				if (removedRobot == null)
				{
					//No robot was destroyed, therefore only thing we need to do is
					//add a fire miss event to the Logger.
					mainController.printEvent("Miss at " + nextAction.getLocationString());
				}
				else
				{
					//If the Robot isn't null it means we successfully hit a Robot and must
					//notify Score Tracker accordingly.
					//Don't want this Running on ActionProcessor thread so we get
					//Main Controller to create a new Task for it.
					
					
					//A robot was destroyed, therefore we must add a hit event to the Logger.
					mainController.printEvent("Robot: " + removedRobot.getUniqueID() + " destroyed at " + nextAction.getLocationString());
					
					//Get the difference between when the click was issued and processed.
					long timeTaken = timeProcessed - nextAction.getTimeCreated();
					
					//Let the controller pass on this message to Score Tracker.
					mainController.notifyScoreTrackerDestroyed(removedRobot, timeTaken);
				}
			}
		}
		catch (InterruptedException interruptedException)
		{
			//Do something here
		}
	}
}