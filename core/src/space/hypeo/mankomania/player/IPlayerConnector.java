package space.hypeo.mankomania.player;


/**
 * Created by pichlermarc on 07.04.2018.
 */

import space.hypeo.mankomania.player.Lobby;

/**
 * Interface provides service methods to fetch data that are relevant for each player
 */
public interface IPlayerConnector {

    /**
     * Ends the current turn.
     */
    public void endTurn();

    /**
     * Sends the Game Lobby broadcast.
     */
    public void broadCastLobby();

}
