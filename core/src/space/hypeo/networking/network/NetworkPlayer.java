package space.hypeo.networking.network;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.minlog.Log;

import java.net.SocketException;

import space.hypeo.mankomania.StageManager;
import space.hypeo.networking.endpoint.Endpoint;
import space.hypeo.networking.endpoint.MClient;
import space.hypeo.networking.endpoint.MHost;
import space.hypeo.networking.packages.Lobby;

/**
 * This class holds the important network data,
 * that identifies a player in the network.
 */
public class NetworkPlayer extends RawPlayer implements IPlayerConnector {

    // The reference to the host or client.
    private Endpoint endpoint;

    /* contains a list of all NetworkPlayer objects,
     * that are connected to the host of the game.
     * even the own NetworkPlayer object itself.
     */
    private Lobby lobby;

    public NetworkPlayer() {
        super();
    }

    /**
     * Creates a new instance of NetworkPlayer.
     * @param nickname
     * @param role
     */
    public NetworkPlayer(String nickname, Role role, StageManager stageManager) {

        super(nickname);

        // TODO: check if WLAN connection is ON and connected to hotspot

        if( endpoint != null ) {
            Log.warn("init: There is already an open connection!");
            return;
        }

        // init endpoint + start process (depends on role)
        if( role == Role.HOST ) {
            endpoint = new MHost(this, stageManager);
            endpoint.start();
        } else if( role == Role.CLIENT ) {
            endpoint = new MClient(this, stageManager);
            endpoint.start();
        } else {
            Log.info("Enpoint could not be initialized for given Role: " + role);
        }

        // insert that player in lobby
        lobby = new Lobby();
        lobby.add(this.getRawPlayer());
    }

    public RawPlayer getRawPlayer() {
        return new RawPlayer(this);
    }

    /**
     * Returns ID of current player.
     * @return String ID.
     */
    @Override
    public String getPlayerID() {
        return super.getPlayerID();
    }

    /**
     * Gets the role in the network.
     * @return Role.
     */
    public Role getRole() {
        if( endpoint == null ) {
            return Role.NOT_CONNECTED;
        }
        return endpoint.getRole();
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void stopEndpoint() {

        if( endpoint != null ) {
            endpoint.stop();
        } else {
            Log.info("No process running - nothing to do.");
        }
    }

    public void closeEndpoint() {

        if( endpoint != null ) {
            endpoint.close();
            endpoint = null;
        } else {
            Log.info("No process running - nothing to do.");
        }
    }

    @Override
    public void changeBalance(String playerID, int amount) {

    }

    @Override
    public void movePlayer(String playerID, int position) {

    }

    @Override
    public void endTurn() {

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
    public Lobby registeredPlayers() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    /**
     * Returns the string representation of current NetworkPlayer object.
     * @return String representation
     */
    @Override
    public String toString() {
        return super.toString()
                + ", Address: " + address
                + ", Role: " + endpoint.getRole();
    }

}
