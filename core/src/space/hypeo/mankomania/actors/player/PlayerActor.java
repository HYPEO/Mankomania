package space.hypeo.mankomania.actors.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.fields.FieldActor;
import space.hypeo.mankomania.actors.map.PlayerDetailActor;

/**
 * Class that represents a NetworkPlayer.
 */
public class PlayerActor extends Group {
    private static final float PLAYER_SCALE = 60f;
    private final StageFactory stageFactory;
    protected FieldActor currentField;

    // Current player state.
    private int balance;
    private boolean isLocal;

    // For dice feature
    private float timeElapsed = 0;
    private Random die = new Random();
    private static final float EARTH_GRAVITY = 9.81f;

    private static final float GRAVITY_FORCE_THRESHOLD = 1.9f;
    private final StageManager manager;
    private PlayerDetailActor playerDetailActor;

    private Image actorImage;

    /**
     * @param balance      The player's current balance (starting balance)
     * @param isLocal      Defines whether this player is the local one (i.e the one controlled with this device)
     * @param stageManager StageManager for pushing DiceStage.
     * @param stageFactory StageFactory for creating new Stages.
     */
    public PlayerActor(int balance, boolean isLocal, final StageManager stageManager, StageFactory stageFactory) {
        this.actorImage = new Image(new Texture("players/player_1.png"));
        this.isLocal = isLocal;
        this.stageFactory = stageFactory;
        this.balance = balance;
        this.manager = stageManager;
    }

    /**
     * Initializes the starting-field and corresponding PlayerDetailActor.
     *
     * @param currentField
     * @param playerDetailActor
     */
    public void initializeState(FieldActor currentField, PlayerDetailActor playerDetailActor) {
        this.playerDetailActor = playerDetailActor;
        this.currentField = currentField;

        actorImage.setBounds(currentField.getX(), currentField.getY(), PLAYER_SCALE, PLAYER_SCALE);
        updateBounds();
    }

    /**
     * Defines whether this player is the local one (i.e the one controlled with this device)
     *
     * @return
     */
    public boolean isLocal() {
        return this.isLocal;
    }

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
                manager.push(stageFactory.getDiceResultStage(moveFields));

                // TODO: maybe cheat function here (for example: if other player is playing roulette)
            }
        }

    }

    /**
     * Moves the player a specific amount of steps on the board.
     *
     * @param steps The amount of steps to move.
     */
    public void move(int steps) {
        currentField = currentField.getFollowingField(steps);
        if (this.isLocal())
            currentField.trigger(this);
        updateBounds();
    }

    /**
     * Updates the object bounds to the current field.
     */
    private void updateBounds() {
        actorImage.setBounds(currentField.getX() + (currentField.getWidth() / 2f) - (this.getWidth() / 2f),
                currentField.getY() + (currentField.getHeight() / 2f) - (this.getHeight() / 2f) + 8f,
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
}
