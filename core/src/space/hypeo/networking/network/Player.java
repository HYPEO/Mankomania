package space.hypeo.networking.network;

import com.esotericsoftware.minlog.Log;

import java.net.SocketException;
import java.util.UUID;

import space.hypeo.networking.endpoint.Endpoint;
import space.hypeo.networking.endpoint.MClient;
import space.hypeo.networking.endpoint.MHost;
import space.hypeo.networking.packages.Lobby;

/**
 * This class holds the important network data,
 * that identifies a player in the network.
 */
public class Player implements IPlayerConnector {

    protected String playerID;  // player ID
    protected String nick;      // nickname
    protected String address;   // IP address in W/LAN

    // The reference to the host or client.
    private Endpoint endpoint;

    /* contains a list of all Player objects,
     * that are connected to the host of the game.
     * even the own Player object itself.
     */
    private Lobby lobby;

    public Player () {}

    /**
     * Creates a new instance of Player.
     * @param nickname
     * @param role
     */
    public Player(String nickname, Role role) {

        this.playerID = generatePlayerID();
        this.nick = nickname;

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
     * Copy constructor.
     * @param p
     */
    public Player(Player p) {
        if (p != null && p != this) {
            this.playerID = p.playerID;
            this.nick = p.nick;
            this.address = p.address;
            this.endpoint = p.endpoint;
            this.lobby = p.lobby;
        }
    }

    /**
     * Returns ID of current player.
     * @return String ID.
     */
    @Override
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Gets the nickname of the current player.
     * @return String nickname.
     */
    public String getNick() {
        return nick;
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

    /**
     * Gets ID for current player.
     * Take from UUID the last 4 characters.
     * @return String PlayerID
     */
    public static String generatePlayerID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(uuid.toString().length() - 4);
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
     * Returns the string representation of current player.
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "PlayerID: " + playerID
                + ", Nick: " + nick
                + ", Address: " + address
                + ", Role: " + endpoint.getRole();
    }

    @Override
    public boolean equals(Object o) {
        if( o == null) { return false; }
        if( o == this) { return true; }

        if( o instanceof Player ) {
            Player other = (Player) o;
            return this.playerID.equals(other.playerID);

        } else if( o instanceof String ) {
            String otherPlayerID = (String) o;
            return this.playerID.equals(otherPlayerID);

        } else {
            return false;
        }
    }
}
