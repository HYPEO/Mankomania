package space.hypeo.networking.player;

import com.esotericsoftware.minlog.Log;

import space.hypeo.mankomania.player.PlayerManager;
import space.hypeo.networking.endpoint.IEndpoint;
import space.hypeo.networking.endpoint.MClient;
import space.hypeo.networking.endpoint.MHost;
import space.hypeo.mankomania.IDeviceStateSubscriber;
import space.hypeo.mankomania.player.IPlayerConnector;
import space.hypeo.mankomania.player.Lobby;
import space.hypeo.networking.network.Role;
import space.hypeo.networking.packages.Remittances;

/**
 * This class holds the important network data,
 * that identifies a player in the network.
 */
public class PlayerNT implements IPlayerConnector, IDeviceStateSubscriber {
    private PlayerManager playerManager;

    // The reference to the host or client.
    private IEndpoint endpoint;

    public PlayerNT(final PlayerManager playerManager) {
        this.playerManager = playerManager;

        Role role = playerManager.getRole();

        if( role == Role.HOST ) {
            endpoint = new MHost(playerManager);
        } else if( role == Role.CLIENT ) {
            endpoint = new MClient(playerManager);
        } else {
            Log.info("Enpoint could not be initialized for given Role: " + role);
        }
    }

    public IEndpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public Lobby getLobby() {
        return playerManager.getLobby();
    }

    public void stopEndpoint() {
        endpoint.stop();
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
        return playerManager.getPlayerBusiness().getPlayerID();
    }
}
