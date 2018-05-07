package space.hypeo.networking;

import com.esotericsoftware.kryonet.Connection;

import space.hypeo.networking.network.Network;

/**
 * The class PlayerInfo holds the important network data,
 * that identifies a client or host.
 */
public class PlayerInfo {

    private String hostName;
    private String address;
    private int port;

    Network.Role role;

    public PlayerInfo() {
        hostName = "";
        address = "";
        port = 0;

        role = null;
    }

    /**
     * Creates a new instance of PlayerInfo
     * @param c kryonet.Connection Connection while has connected/disconneted/received
     */
    public PlayerInfo(Connection c, Network.Role r) {

        if( c == null || r == null ) {
            new PlayerInfo();

        } else {
            address = c.getRemoteAddressTCP().getAddress().toString();
            hostName = c.getRemoteAddressTCP().getHostName();
            port = c.getRemoteAddressTCP().getPort();
            role = r;
        }
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
}
