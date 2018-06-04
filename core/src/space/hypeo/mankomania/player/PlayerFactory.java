package space.hypeo.mankomania.player;

import space.hypeo.networking.endpoint.EndpointFactory;
import space.hypeo.networking.endpoint.IEndpoint;
import space.hypeo.networking.player.PlayerNT;

public class PlayerFactory {
    private final PlayerManager playerManager;

    public final static int START_BALANCE = 1000000;

    public PlayerFactory(final PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public PlayerSkeleton getPlayerSkeleton(String nick) {
        return new PlayerSkeleton(nick);
    }

    public PlayerNT getPlayerNT() {
        EndpointFactory endpointFactory = new EndpointFactory(playerManager);
        IEndpoint endpoint = endpointFactory.getEndpoint();
        return new PlayerNT(playerManager, endpoint);
    }
}
