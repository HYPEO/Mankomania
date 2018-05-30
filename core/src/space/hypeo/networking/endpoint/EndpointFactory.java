package space.hypeo.networking.endpoint;

import com.esotericsoftware.minlog.Log;

import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.networking.network.Role;

public class EndpointFactory {
    private final PlayerManager playerManager;

    public EndpointFactory(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public MHost getHost() {
        return new MHost(playerManager);
    }

    public MClient getClient() {
        return new MClient(playerManager);
    }

    public IEndpoint getEndpoint() {
        IEndpoint endpoint;
        if( playerManager.getRole() == Role.HOST ) {
            endpoint = new MHost(playerManager);
        } else if( playerManager.getRole() == Role.CLIENT ) {
            endpoint = new MClient(playerManager);
        } else {
            Log.info("Enpoint could not be initialized for given Role: " + playerManager.getRole());
            endpoint = null;
        }
        return endpoint;
    }
}
