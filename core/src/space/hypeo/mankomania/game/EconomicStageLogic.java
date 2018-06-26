package space.hypeo.mankomania.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import space.hypeo.mankomania.actors.player.PlayerActor;

public class EconomicStageLogic {

    private PlayerActor playerActor;
    private Preferences pref;

    public EconomicStageLogic(PlayerActor playerActor, String pref) {
        this.playerActor = playerActor;
        initPref(pref);
    }

    public EconomicStageLogic(PlayerActor playerActor){
        this.playerActor=playerActor;
    }

    public void payMoney(int money) {
        if (playerActor.getBalance() - money < 0) {
            playerActor.setBalance(0);
        } else {
            playerActor.setBalance(playerActor.getBalance() - money);
        }
    }

    public void getMoney(int money) {
        playerActor.setBalance(playerActor.getBalance() + money);

    }

    public void initPref(String name) {
        pref = Gdx.app.getPreferences(name);

    }

    public void setPrefs(String name, int money) {
        pref.putInteger(name, money);
        pref.flush();

    }

    public int getPrefs(String name) {
        return pref.getInteger(name, 0);

    }
    public int getBalance(){
        return playerActor.getBalance();
    }

}