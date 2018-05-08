package space.hypeo.networking;

import java.util.HashMap;
import java.util.List;


/**
 * Created by pichlermarc on 07.04.2018.
 */

/**
 * Interface provides service methods to fetch data that are relevant for each player
 */
public interface IPlayerConnector {
    /**
     * Changes the balance of the player with the specified playerID.
     * @param playerID
     * @param amount
     */
    public void changeBalance(String playerID, int amount);

    /**
     * Moves the player with the specified playerID to the field with the specified positionID.
     * @param playerID
     * @param position
     */
    public void movePlayer(String playerID, int position);

    /**
     * Ends the current turn.
     */
    public void endTurn();

    /**
     * Returns the balance of the player with the specified playerID.
     * @param playerID
     * @return
     */
    public int getPlayerBalance(String playerID);

    /**
     * Gets a player's position on the field.
     * @param playerID
     * @return
     */
    public int getPlayerPosition(String playerID);

    /**
     * Returns the current player's ID.
     * @return
     */
    public String getCurrentPlayerID();

    /**
     * Gets the list of players registered for the Game.
     */
    public Players registeredPlayers();

}
