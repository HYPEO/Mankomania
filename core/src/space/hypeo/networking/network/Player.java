package space.hypeo.networking.network;

import com.esotericsoftware.kryonet.Connection;

/**
 * This class holds the important network data,
 * that identifies a player in the network.
 */
public class Player {

    private String playerID;
    private String nick;
    private String hostName;
    private String address;
    private int port;

    Role role;

    /**
     * Creates a new instance of PlayerInfo
     * Default Constructor
     */
    public Player() {

        playerID = "";
        nick = "";

        hostName = "";
        address = "";
        port = 0;

        role = null;
    }

    /**
     * Creates a new instance of PlayerInfo
     * @param c kryonet.Connection Connection while has connected/disconneted/received
     */
    public Player(Connection c, Role r) {

        if( c == null || r == null ) {
            new Player();

        } else {
            playerID = "";
            nick = "";
            address = c.getRemoteAddressTCP().getAddress().toString();
            hostName = c.getRemoteAddressTCP().getHostName();
            port = c.getRemoteAddressTCP().getPort();
            role = r;
        }
    }

    /**
     * Creates a new instance of PlayerInfo
     * @param playerID
     * @param nick
     * @param hostName
     * @param address
     * @param port
     * @param role
     */
    public Player(String playerID, String nick,
                  String hostName, String address, int port,
                  Role role) {
        this.playerID = "";
        this.nick = "";
        this.hostName = hostName;
        this.address = address;
        this.port = port;
        this.role =role;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Gets current hostname
     * @return String Hostname
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Gets current address
     * @return String IP Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets current port number
     * @return int Port number
     */
    public int getPort() {
        return port;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "PlayerID: " + playerID
                + ", Nick: " + nick
                + ", Hostname: " + hostName
                + ", Address: " + address
                + ", Port: " + port
                + ", Role: " + role;
    }
}
