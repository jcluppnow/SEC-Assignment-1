public class RobotFactory
{
    private int currentID;
    
    public RobotFactory()
    {
        currentID = 1;
    }
    
    public Robot createRobot()
    {
        Robot newRobot = new Robot(currentID);
        currentID++;
        
        return newRobot;
    }
    
    public Robot createRobot(int inDelay)
    {
        Robot newRobot = new Robot(currentID, inDelay);
        currentID++;
        
        return newRobot;
    }
}