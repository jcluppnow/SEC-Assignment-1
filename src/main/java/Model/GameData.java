public class GameData
{
    private int score;
    private long startGameTime, elapsedGameTime;
    
    public GameData()
    {
        score = 0;
        startGameTime = 0;  //Set to 0 for now, but will eventually be set to when the game starts.
        elapsedGameTime = 0;
    }
    
    public GameData(long inStartTime)
    {
        score = 0;
        startGameTime = inStartTime;
        elapsedGameTime = 0;
    }

    public void timeScoreIncrease()
    {
        score = score + 10;   
    }
    
    public void robotScoreIncrease(int playerTimeDelay,  int robotTimeDelay)
    {
        score = score + (10 + (100 * (playerTimeDelay / robotTimeDelay)));
    }
    
    public int getScore()
    {
       return score;
    }
}
