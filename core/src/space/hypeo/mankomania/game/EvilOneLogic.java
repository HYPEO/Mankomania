package space.hypeo.mankomania.game;

import space.hypeo.mankomania.actors.player.PlayerActor;

public class EvilOneLogic {
    private PlayerActor playerActor;
    int totaldiceMoney;
    public EvilOneLogic(PlayerActor playerActor)
    {
        this.playerActor = playerActor;
    }
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
    public int getTotalDiceMoney()
    {
        return totaldiceMoney;
    }
    public void setTotaldiceMoney(int totaldiceMoney)
    {
        this.totaldiceMoney = totaldiceMoney;
    }
    public void updatePlayerBalance()
    {
        playerActor.changeBalance(totaldiceMoney*(-1));
    }
}
