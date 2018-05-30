package space.hypeo.networking.player;

import com.esotericsoftware.minlog.Log;

import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.mankomania.player.PlayerSkeleton;
import space.hypeo.networking.endpoint.IEndpoint;
import space.hypeo.mankomania.IDeviceStateSubscriber;
import space.hypeo.mankomania.player.IPlayerConnector;
import space.hypeo.mankomania.player.Lobby;
import space.hypeo.networking.endpoint.IHostConnector;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.packages.Remittances;

/**
 * This class is a wrapper class for an endpoint.
 * An endpoint can be server or client.
 * The class represents the network connection of the current player and
 * communicates with other endpoints.
 */
public class PlayerNT implements IPlayerConnector, IDeviceStateSubscriber {
    private final PlayerManager playerManager;
    private final IEndpoint endpoint;

    public PlayerNT(final PlayerManager playerManager, final IEndpoint endpoint) {
        this.playerManager = playerManager;
        this.endpoint = endpoint;
    }

    public IEndpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public Lobby getLobby() {
        return playerManager.getLobby();
    }

    @Override
    public void changeBalance(String playerID, int amount) {
        Remittances remittances = new Remittances(this.getPlayerID(), playerID, amount);
        endpoint.changeBalance(remittances);
    }

    @Override
    public void movePlayer(String playerID, int position) {
        // TODO: move player on the map
    }

    @Override
    public void endTurn() {
        // TODO: end the current turn for this player
    }

    @Override
    public int getPlayerBalance(String playerID) {
        return 0;
    }

    @Override
    public int getPlayerPosition(String playerID) {
        return 0;
    }

    @Override
    public void onPause() {
        endpoint.stop();
    }

    @Override
    public void onStop() {
        endpoint.stop();
    }

    @Override
    public String getPlayerID() {
        return playerManager.getPlayerSkeleton().getPlayerID();
    }

    @Override
    public void broadCastLobby() {
        endpoint.broadCastLobby();
    }

    public void kickPlayerFromLobby(PlayerSkeleton playerToKick) {
        if(playerManager.getRole() == Role.HOST) {
            Log.info("PlayerNT: Try to kick player " + playerToKick);
            IHostConnector host = (IHostConnector) endpoint;
            host.sendOrderToCloseConnection(playerToKick);
        }
    }
}
