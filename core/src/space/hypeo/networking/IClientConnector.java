package space.hypeo.networking;

/**
 * Interface provides service methods for player that is game-client
 */
public interface IClientConnector {

    /**
     * Joins the game another host has set up.
     * @param playerID
     * @return
     */
    public boolean joinGame(String playerID);

    /**
     * Starts the client
     */
    public void startClient();

}
