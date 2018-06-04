package space.hypeo.mankomania.actors.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.Random;

import space.hypeo.mankomania.GameStateManager;
import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.fields.FieldActor;
import space.hypeo.mankomania.actors.map.PlayerDetailActor;

/**
 * Class that represents a Player.
 */
public class PlayerActor extends Group {
    private static final float PLAYER_SCALE = 60f;

    // Current player state.
    private int balance;
    protected FieldActor currentField;
    private float timeElapsed = 0;
    private Random die = new Random();
    protected boolean isActive;
    private static final float EARTH_GRAVITY = 9.81f;
    private static final float GRAVITY_FORCE_THRESHOLD = 1.9f;
    private StageManager stageManager;
    private StageFactory stageFactory;
    private GameStateManager gameStateManager;


    // UI Relevant
    private PlayerDetailActor playerDetailActor;
    private Image actorImage;

    /**
     * @param actorImage Image that represents the actor.
     * @param balance    The player's current balance (starting balance)
     */
    public PlayerActor(Image actorImage, int balance) {
        this.actorImage = actorImage;
        this.addActor(this.actorImage);
        this.balance = balance;
        this.isActive = false;
    }

    /**
     * Initializes the starting-field and corresponding PlayerDetailActor.
     *
     * @param currentField      The field this Player starts out at.
     * @param playerDetailActor The PlayerDetailActor that belongs to this player.
     */
    public void initializeState(FieldActor currentField, PlayerDetailActor playerDetailActor) {
        this.playerDetailActor = playerDetailActor;
        this.currentField = currentField;

        actorImage.setBounds(currentField.getX(), currentField.getY(), PLAYER_SCALE, PLAYER_SCALE);
        updateBounds();
    }

    /**
     <<<<<<< HEAD
     * Defines whether this player is the local one (i.e the one controlled with this device)
     *
     * @return
     */


    @Override
    public void act(float deltaTime) {

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

    /**
     =======
     >>>>>>> ae092e7a79e765e4d4245e2a0bd85e0754ef1c06
     * Moves the player a specific amount of steps on the board.
     *
     * @param steps The amount of steps to move.
     */
    public void move(int steps) {
        currentField = currentField.getFollowingField(steps);
        updateBounds();
    }

    /**
     * Updates the object bounds to the current field.
     */
    private void updateBounds() {
        actorImage.setBounds(currentField.getX() + (currentField.getWidth() / 2f) - (actorImage.getWidth() / 2f),
                currentField.getY() + (currentField.getHeight() / 2f) - (actorImage.getHeight() / 2f) + 8f,
                PLAYER_SCALE,
                PLAYER_SCALE);
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
        this.playerDetailActor.updateBalance(balance);
    }

    public void changeBalance(int remittance) {
        this.balance += remittance;
    }

    public void setActive() {
        this.isActive = true;
    }

    public void setInactive() {
        this.isActive = false;
    }
}