package space.hypeo.mankomania.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.Random;

/**
 * Class that represents a Player.
 */
public class PlayerActor extends Image {
    private static final float PLAYER_SCALE = 20f;
    private FieldActor currentField;

    // Current player state.
    private int balance;
    private boolean isLocal;
    private String playerID;

    // For demostration purposes only. TODO: Remove!
    private float timeElapsed = 0;
    private Random die = new Random();

    /**
     * Creates a new instance of a Class that implementaion for a Player.
     *
     * @param playerID     The player's ID (useful for communications)
     * @param balance      The player's current balance (starting balance)
     * @param isLocal      Defines whether this player is the local one (i.e the one controlled with this device)
     * @param currentField Defines the players current position.
     */
    public PlayerActor(String playerID, int balance, boolean isLocal, FieldActor currentField) {
        super(new Texture("tile.png"));
        this.currentField = currentField;
        this.setBounds(currentField.getX(), currentField.getY(), PLAYER_SCALE, PLAYER_SCALE);
        this.isLocal = isLocal;
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
    public void act(float deltaTime)
    {
        timeElapsed += deltaTime;
        if (timeElapsed >= 0.5f) {
            timeElapsed = 0;
            this.move(die.nextInt(6) + 1);
        }
    }

    /**
     * Moves the player a specific amount of steps on the board.
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
        this.setBounds(currentField.getX(), currentField.getY(), PLAYER_SCALE, PLAYER_SCALE);
    }
}
