package space.hypeo.networking.wifi;

import java.util.HashMap;
import java.util.List;

/**
 * Wifi Peer 2 Peer "Direct" Connector as Singleton
 */
public class WifiConnector {

    private static WifiConnector instance;

    private WifiConnector() {}

    /**
     * Fetch the instance of the WifiConnector
     * @return instance of WifiConnector
     */
    public static WifiConnector getInstance() {
        if( instance == null ) {
            instance = new WifiConnector();
        }

        return instance;
    }

    /**
     * Registers the application with the Wi-Fi framework.
     * This must be called before calling any other Wi-Fi P2P method.
     */
    public void initialize() {
    }

    /**
     * Creates a peer-to-peer group with the current device as the group owner.
     */
    public void createGroup() {
    }

    /**
     * Initiates peer discovery.
     */
    public void fetchListOfPeers() {
    }

    /**
     * Broadcast data to all peers.
     * @return List of players registered for the Game
     */
    public List<String> broadCast() {
        return null;
    }

    /**
     * Connect to a Peer.
     * @param playerID ID of player
     */
    public void connectToPeer(String playerID) {
    }

    /**
     * Transfere data to peer that connected to.
     * @param data
     */
    public void transfereDataToPeer(HashMap<String, String> data) {
    }
}
