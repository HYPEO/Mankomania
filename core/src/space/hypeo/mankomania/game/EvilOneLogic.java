package space.hypeo.mankomania.game;

import space.hypeo.mankomania.actors.player.PlayerActor;

public class EvilOneLogic {
    private PlayerActor playerActor;
    int totaldiceMoney;
    /**
     * Creates instance of EvilOneLogic.
     * @param playerActor
     */
    public EvilOneLogic(PlayerActor playerActor)
    {
        this.playerActor = playerActor;
    }
    /**
     * Checks the Dice results and sets the totalDiceMoney
     * @param diceResult1
     * @param diceResult2
     */
    public boolean checkDiceResults(int diceResult1, int diceResult2)
    {
        if(diceResult1 == 1 || diceResult2 == 1)
        {
            if(diceResult1 == 1 && diceResult2 == 1)
                totaldiceMoney = -300000;
            else
                totaldiceMoney = -100000;
            updatePlayerBalance();
            return false;
        }
        else
        {
            totaldiceMoney = totaldiceMoney + 5000 * (diceResult1 + diceResult2);
            return true;
        }
    }
    /**
     * Get the total dice money
     */
    public int getTotalDiceMoney()
    {
        return totaldiceMoney;
    }
    /**
     * Sets the total dice money (for testing purposes)
     * @param totaldiceMoney
     */
    public void setTotaldiceMoney(int totaldiceMoney)
    {
        this.totaldiceMoney = totaldiceMoney;
    }
    /**
     * Updates the players Balance in the PlayerActor
     */
    public void updatePlayerBalance()
    {
        playerActor.changeBalance(totaldiceMoney*(-1));
    }
}
