package space.hypeo.networking;

public interface IHostConnector {

    /**
     * Advertises a new Game that other players can join.
     */
    public void advertiseGame();

    /**
     * Starts the game (requires that a game has been advertised by this player)
     */
    public boolean startGame();

}
