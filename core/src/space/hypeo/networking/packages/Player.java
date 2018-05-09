package space.hypeo.networking.packages;

import com.esotericsoftware.kryonet.Connection;

import space.hypeo.networking.network.CRole;

/**
 * The class Player holds the important network data,
 * that identifies a client or host.
 */
public class Player {

    private String hostName;
    private String address;
    private int port;

    CRole cRole;

    /**
     * Creates a new instance of PlayerInfo
     * Default Constructor
     */
    public Player() {
        hostName = "";
        address = "";
        port = 0;

        cRole = null;
    }

    /**
     * Creates a new instance of PlayerInfo
     * @param c kryonet.Connection Connection while has connected/disconneted/received
     */
    public Player(Connection c, CRole.Role r) {

        if( c == null || r == null ) {
            new Player();

        } else {
            address = c.getRemoteAddressTCP().getAddress().toString();
            hostName = c.getRemoteAddressTCP().getHostName();
            port = c.getRemoteAddressTCP().getPort();
            cRole = new CRole(r);
        }
    }

    /**
     * Creates a new instance of PlayerInfo
     * @param hostName
     * @param address
     * @param port
     * @param cRole
     */
    public Player(String hostName, String address, int port, CRole.Role cRole) {
        this.hostName = hostName;
        this.address = address;
        this.port = port;
        this.cRole = new CRole(cRole);
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

    @Override
    public String toString() {
        return "Hostname: '" + hostName
                + "', Address: '" + address
                + "', Port: " + port
                + "', Role: '" + cRole.toString();
    }
}
