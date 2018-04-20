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

    /**
     *
     * @param connector
     */
    public FieldBehaviour(IPlayerConnector connector, GameState gameState)
    {
        this.connector = connector;
    }

    /**
     * Triggers this field for the player.
     * @param player
     */
    public abstract void trigger(PlayerBehaviour player);

    public Vector3 getFieldPosition()
    {
        return this.gameObject.getPosition();
    }

    public void setFieldPosition(Vector3 position)
    {
        this.gameObject.setPosition(position);
    }

    public FieldBehaviour getFollowingField(int steps)
    {
        return this.nextField.getFollowingField(steps-1);
    }

    public void setNextField(FieldBehaviour nextField)
    {
        this.nextField = nextField;
    }
}
