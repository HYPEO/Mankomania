package space.hypeo.mankomania.actors.player;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import space.hypeo.mankomania.GameStateManager;
import space.hypeo.mankomania.actors.map.PlayerDetailActor;
import space.hypeo.mankomania.sensor.DiceSensorManager;

/**
 * Created by pichlermarc on 19.05.2018.
 */
public class LocalPlayerActor extends PlayerActor {
    private DiceSensorManager diceSensorManager;
    private Image yourTurn;
    private float timeElapsed;

    /**
     * @param actorImage Image that represents the actor.
     * @param balance    The player's current balance (starting balance)
     */
    public LocalPlayerActor(Image actorImage, int balance, DiceSensorManager diceSensorManager, GameStateManager gameStateManager, PlayerDetailActor playerDetailActor, String id, String nickname, Image yourTurn) {
        super(actorImage, balance, playerDetailActor, gameStateManager, id, nickname);
        this.diceSensorManager = diceSensorManager;
        this.yourTurn = yourTurn;
        timeElapsed = 0;

        yourTurn.setVisible(this.isActive);
        this.addActor(yourTurn);
    }

    @Override
    public void setActive() {
        super.setActive();
        if(yourTurn!=null)
            yourTurn.setVisible(this.isActive);
    }

    @Override
    public void setInactive() {
        super.setInactive();
        if(yourTurn!=null)
            yourTurn.setVisible(this.isActive);
    }

    @Override
    public void move(int steps) {
        super.move(steps);
        currentField.trigger(this);

        if (this.getBalance() <= 0)
            gameStateManager.setWinner(this);
        gameStateManager.endTurn();
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        if (this.isActive) {
            timeElapsed += deltaTime;
            if (timeElapsed >= 0.18f) {
                timeElapsed = 0;
                diceSensorManager.trigger(this);
            }
        }
    }
}
