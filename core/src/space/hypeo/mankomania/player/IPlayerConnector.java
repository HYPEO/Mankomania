package space.hypeo.mankomania.player;


/**
 * Created by pichlermarc on 07.04.2018.
 */

/**
 * Interface provides service methods to fetch data that are relevant for each player
 */
public interface IPlayerConnector {

    /**
     * Ends the current turn.
     */
    void endTurn();

    /**
     * Sends the Game Lobby broadcast.
     */
    void broadCastLobby();

    /**
     * Sends the name of the winning horse over the network.
     * @param horseName name of the winning horse.
     */
    void sendHorseRaceResult(String horseName);

    /**
     * Sends the id of the winning slot over the network.
     * @param slotId number of the winning slot.
     */
    void sendRouletteResult(int slotId);

    /**
     * Disconnects current player.
     */
    void disconnect();
}
