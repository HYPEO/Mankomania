package space.hypeo.mankomania.behaviour;

import com.badlogic.gdx.math.Vector3;

import java.lang.reflect.Field;

import space.hypeo.mankomania.GameState;
import space.hypeo.networking.IPlayerConnector;
import space.hypeo.spriteforce.Behaviour;

/**
 * Created by pichlermarc on 07.04.2018.
 */

/**
 * Specifies what happens to players on a field.
 */
public abstract class FieldBehaviour extends Behaviour {

    protected IPlayerConnector connector;
    protected GameState state;
    protected FieldBehaviour nextField;

    public FieldBehaviour(IPlayerConnector connector, GameState gameState) {
        this.connector = connector;
        this.state = gameState;
    }

    /**
     * Triggers this field for the player.
     *
     * @param player
     */
    public abstract void trigger(PlayerBehaviour player);

    public Vector3 getFieldPosition() {
        return this.gameObject.getPosition();
    }

    public void setFieldPosition(Vector3 position) {
        this.gameObject.setPosition(position);
    }


    public Vector3 getFieldScale() {
        return this.gameObject.getScale();
    }

    public void setFieldScale(Vector3 scale) {
        this.gameObject.setScale(scale);
    }

    /**
     * Gets the FieldBehavior of the field n steps from this one.
     *
     * @param steps Amount of steps to look ahead.
     * @return The Field located n steps from this one.
     */
    public FieldBehaviour getFollowingField(int steps) {
        if (steps > 0)
            return this.nextField.getFollowingField(steps - 1);
        else
            return this;
    }

    /**
     * Links up the next field with the specified one.
     *
     * @param nextField
     */
    public void setNextField(FieldBehaviour nextField) {
        this.nextField = nextField;
    }
}
