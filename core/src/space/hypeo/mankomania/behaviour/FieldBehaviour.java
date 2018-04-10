package space.hypeo.mankomania.behaviour;

import com.badlogic.gdx.math.Vector3;

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

    IPlayerConnector connector;
    GameState state;

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
}
