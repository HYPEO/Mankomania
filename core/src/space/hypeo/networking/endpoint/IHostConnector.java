package space.hypeo.networking.endpoint;

import space.hypeo.mankomania.player.PlayerSkeleton;

/**
 * Interface provides service methods for player that is game-host
 */
public interface IHostConnector {

    /**
     * Advertises a new Game that other players can join.
     */
    void advertiseGame();

    /**
     * Starts the game (requires that a game has been advertised by this player)
     */
    boolean startGame();

    /**
     * Ends the game.
     * Release all resources and close all open connections.
     */
    void endGame();

    /**
     * Send client the order to close its connection to the host.
     * @param playerToKick
     */
    void sendOrderToCloseConnection(PlayerSkeleton playerToKick);

}
