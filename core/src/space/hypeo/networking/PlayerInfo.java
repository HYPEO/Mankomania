package space.hypeo.networking;

import com.esotericsoftware.kryonet.Connection;

/**
 * The class PlayerInfo holds the important network data,
 * that identifies a client or host.
 */
public class PlayerInfo {

    private String hostName;
    private String address;
    private int port;

    /**
     * Creates a new instance of PlayerInfo
     * @param c kryonet.Connection Connection while has connected/disconneted/received
     */
    public PlayerInfo(Connection c) {
        address = c.getRemoteAddressTCP().getAddress().toString();
        hostName = c.getRemoteAddressTCP().getHostName();
        port = c.getRemoteAddressTCP().getPort();
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
