package space.hypeo.networking.endpoint;

import space.hypeo.mankomania.player.PlayerManager;

public class EndpointFactory {
    private final PlayerManager playerManager;

    public EndpointFactory(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public MHost getHost() {
        return new MHost(playerManager);
    }

    public MClient getClient() {
        return new MClient(playerManager);
    }
}
