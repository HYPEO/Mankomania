package space.hypeo.mankomania.actors.player;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

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
    protected boolean isActive;


    // UI Relevant
    private PlayerDetailActor playerDetailActor;
    private Image actorImage;

    /**
     * @param actorImage   Image that represents the actor.
     * @param balance      The player's current balance (starting balance)
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

    public void setActive()
    {
        this.isActive = true;
    }

    public void setInactive()
    {
        this.isActive = false;
    }
}
