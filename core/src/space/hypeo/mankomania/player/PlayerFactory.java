package space.hypeo.mankomania.player;

import com.esotericsoftware.kryonet.EndPoint;

import space.hypeo.networking.endpoint.EndpointFactory;
import space.hypeo.networking.endpoint.IEndpoint;
import space.hypeo.networking.player.PlayerNT;

public class PlayerFactory {
    private final PlayerManager playerManager;

    public PlayerFactory(final PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public PlayerBusiness getPlayerBusiness(final String nickname) {

        return new PlayerBusiness(nickname, playerManager);
    }

    public PlayerNT getPlayerNT() {
        EndpointFactory endpointFactory = new EndpointFactory(playerManager);
        IEndpoint endpoint = endpointFactory.getEndpoint();
        return new PlayerNT(playerManager, endpoint);
    }
}