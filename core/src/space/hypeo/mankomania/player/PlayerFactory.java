package space.hypeo.mankomania.player;

import space.hypeo.networking.endpoint.EndpointFactory;
import space.hypeo.networking.endpoint.IEndpoint;
import space.hypeo.networking.player.PlayerNT;

/**
 * This class is a factory to create instances of the different player in layer:
 *  - Business Layer
 *  - Network Layer
 */
public class PlayerFactory {
    private final PlayerManager playerManager;

    public static final int START_BALANCE = 1000000;

    /**
     * Create instance of PlayerFactory.
     * @param playerManager
     */
    public PlayerFactory(final PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    /**
     * Gets used PlayerManager.
     * @return
     */
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    /**
     * Creates instance of PlayerSkeleton.
     * @param nick
     * @return
     */
    public PlayerSkeleton getPlayerSkeleton(String nick) {
        return new PlayerSkeleton(nick);
    }

    /**
     * Creates instance of PlayerNT.
     * @return
     */
    public PlayerNT getPlayerNT() {
        EndpointFactory endpointFactory = new EndpointFactory(playerManager);
        IEndpoint endpoint = endpointFactory.getEndpoint();
        return new PlayerNT(playerManager, endpoint);
    }
}
