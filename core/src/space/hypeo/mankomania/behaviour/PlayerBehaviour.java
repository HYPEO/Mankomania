package space.hypeo.mankomania.behaviour;

import space.hypeo.mankomania.behaviour.FieldBehaviour;
import space.hypeo.spriteforce.Behaviour;

/**
 * Created by pichlermarc on 07.04.2018.
 */

/**
 * The PlayerBehaviour Class defines all the logic that moves the player, and more.
 */
public class PlayerBehaviour extends Behaviour {
    private int balance;
    private boolean isLocal;
    private String playerID;
    private FieldBehaviour field;

    /**
     * Creates a new instance of a Class that conatins Behaviour-Specific implementation for a player.
     * @param playerID  The player's ID (useful for communications)
     * @param balance   The player's current balance (starting balance)
     * @param isLocal   Defines whether this player is the local one (i.e the one controlled with this device)
     * @param field     Defines the players current position.
     */
    public PlayerBehaviour(String playerID, int balance, boolean isLocal, FieldBehaviour field)
    {
        this.playerID = playerID;
        this.isLocal = isLocal;
        this.balance = balance;
        this.field = field;
    }

    /**
     * Defines whether this player is the local one (i.e the one controlled with this device)
     * @return
     */
    public boolean isLocal()
    {
        return this.isLocal;
    }

    @Override
    public void initialize() {
        //TODO: Implement initialization behavviour.
    }

    @Override
    public void update(float deltaTime) {
        //TODO: Implement player behaviour...
    }
}
