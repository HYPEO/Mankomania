package space.hypeo.networking.network;

import com.esotericsoftware.minlog.Log;

import java.net.SocketException;

import space.hypeo.networking.endpoint.Endpoint;
import space.hypeo.networking.endpoint.MClient;
import space.hypeo.networking.endpoint.MHost;
import space.hypeo.networking.packages.Lobby;

/**
 * This class holds the important network data,
 * that identifies a player in the network.
 */
public class Player extends RawPlayer implements IPlayerConnector {

    private String address;   // IP address in W/LAN

    // The reference to the host or client.
    private Endpoint endpoint;

    /* contains a list of all Player objects,
     * that are connected to the host of the game.
     * even the own Player object itself.
     */
    private Lobby lobby;

    public Player () {
        super();
    }

    /**
     * Creates a new instance of Player.
     * @param nickname
     * @param role
     */
    public Player(String nickname, Role role) {

        super(nickname);

        // TODO: check if WLAN connection is ON and connected to hotspot

        if( endpoint != null ) {
            Log.warn("init: There is already an open connection!");
            return;
        }

        // fetch IP in W/LAN
        String currentIpAddr = "";
        try {
            currentIpAddr = NetworkAddress.getNetworkAddress();
            Log.info( "current IP address: " + currentIpAddr );
        } catch(SocketException e) {
            Log.info(e.getMessage());
        }
        this.address = currentIpAddr;

        // init endpoint + start process (depends on role)
        if( role == Role.HOST ) {
            endpoint = new MHost(this);
            endpoint.start();
        } else if( role == Role.CLIENT ) {
            endpoint = new MClient(this);
            endpoint.start();
        } else {
            Log.info("Enpoint could not be initialized for given Role: " + role);
        }

        // insert that player in lobby
        lobby = new Lobby();
        lobby.put(playerID, this);
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
     * Gets the nickname of the current player.
     * @return String nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets current address.
     * @return String IP Address
     */
    public String getAddress() {
        return address;
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
     * Returns the string representation of current Player object.
     * @return String representation
     */
    @Override
    public String toString() {
        return super.toString()
                + ", Address: " + address
                + ", Role: " + endpoint.getRole();
    }

}
