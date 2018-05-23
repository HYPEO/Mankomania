package space.hypeo.mankomania.actors.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.Random;

import space.hypeo.mankomania.GameStateManager;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;

/**
 * Created by pichlermarc on 19.05.2018.
 */
public class LocalPlayerActor extends PlayerActor {
    // For dice feature
    private float timeElapsed = 0;
    private Random die = new Random();
    private static final float EARTH_GRAVITY = 9.81f;
    private static final float GRAVITY_FORCE_THRESHOLD = 1.9f;
    private StageManager stageManager;
    private StageFactory stageFactory;
    private GameStateManager gameStateManager;

    /**
     * @param actorImage   Image that represents the actor.
     * @param balance      The player's current balance (starting balance)
     * @param stageManager StageManager for pushing DiceStage.
     * @param stageFactory StageFactory for creating new Stages.
     */
    public LocalPlayerActor(Image actorImage, int balance, StageManager stageManager, StageFactory stageFactory, GameStateManager gameStateManager) {
        super(actorImage, balance);
        this.stageManager = stageManager;
        this.stageFactory = stageFactory;
        this.gameStateManager = gameStateManager;
        this.gameStateManager.addPlayer(this);
    }

    @Override
    public void move(int steps) {
        super.move(steps);
        currentField.trigger(this);
        if(this.getBalance()<0)
            gameStateManager.setWinner(this);
        gameStateManager.endTurn();
    }

    @Override
    public void act(float deltaTime) {

        if (this.isActive) {
            float xValue;
            float yValue;
            float zValue;
            float gForce;

            timeElapsed += deltaTime;
            if (timeElapsed >= 0.18f) {
                timeElapsed = 0;

                xValue = Gdx.input.getAccelerometerX() / EARTH_GRAVITY;
                yValue = Gdx.input.getAccelerometerY() / EARTH_GRAVITY;
                zValue = Gdx.input.getAccelerometerZ() / EARTH_GRAVITY;

                gForce = (float) Math.sqrt(xValue * xValue + yValue * yValue + zValue * zValue);

                if (gForce > GRAVITY_FORCE_THRESHOLD) {

                    // TODO: check if it is the players turn, then move
                    int moveFields = die.nextInt(6) + 1;
                    this.move(moveFields);
                    stageManager.push(stageFactory.getDiceResultStage(moveFields));

                    // TODO: maybe cheat function here (for example: if other player is playing roulette)
                }
            }
        }
    }
}
