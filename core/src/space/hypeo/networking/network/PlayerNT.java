package space.hypeo.networking.network;

import com.esotericsoftware.minlog.Log;

import space.hypeo.Player.PlayerManager;
import space.hypeo.networking.endpoint.IEndpoint;
import space.hypeo.networking.packages.Remittances;

/**
 * This class holds the important network data,
 * that identifies a player in the network.
 */
public class PlayerNT implements IEndpoint, IPlayerConnector, IDeviceStateSubscriber {
    private PlayerManager playerManager;

    // The reference to the host or client.
    private IEndpoint endpoint;

    public PlayerNT(final PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public IEndpoint getEndpoint() {
        return endpoint;
    }

    @Override
    public void stop() {
        if( endpoint != null ) {
            endpoint.stop();
        } else {
            Log.info("No process running - nothing to do.");
        }
    }

    @Override
    public void close() {
        if( endpoint != null ) {
            endpoint.close();
            endpoint = null;

            // TODO: return to main menu

        } else {
            Log.info("No process running - nothing to do.");
        }
    }

    @Override
    public Lobby getLobby() {
        return playerManager.getLobby();
    }

    @Override
    public void toggleReadyStatus(PlayerSkeleton player2toggleReadyStatus) {
    }

    @Override
    public void changeBalance(Remittances remittances) {
    }

    public void stopEndpoint() {
    }

    public void closeEndpoint() {
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
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void onPause() {
        endpoint.close();
    }

    @Override
    public void onStop() {
        endpoint.close();
    }

    @Override
    public String getPlayerID() {
        return playerManager.getPlayerBusiness().getPlayerID();
    }
}
