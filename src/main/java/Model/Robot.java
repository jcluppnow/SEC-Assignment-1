import java.util.Random;

public class Robot
{
    private int uniqueID, delay;
 
    public Robot(int inID)
    {
        uniqueID = inID;
        delay = getRandomNumber(500, 2000);
        
    }
    
    public Robot(int inID, int inDelay)
    {
        uniqueID = inID;
        delay = inDelay;
    }
    
    //Accessors
    public int getUniqueID()
    {
        return uniqueID;
    }
    
    public int getDelay()
    {
        return delay;
    }
    
    private int getRandomNumber(int lowerBound, int upperBound)
    {
        int randomNumber = -1;
        Random rand = new Random();
        
        randomNumber = rand.nextInt(upperBound-lowerBound) + lowerBound;
        return randomNumber;
    }
}