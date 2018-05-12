package space.hypeo.networking.network;

import java.net.InetAddress;
import java.util.List;

/**
 * Interface provides service methods for player that is game-client
 */
public interface IClientConnector {

    /**
     * Joins the game another host has set up.
     * @param playerID
     * @return
     */
    public boolean joinGame(String playerID);

    /**
     * Starts the client network thread.
     * This thread is what receives (and sometimes sends) data over the network
     */
    public void startClient();

    /**
     * Closes any network connection AND stops the client network thread.
     */
    public void stopClient();

    /**
     * Closes the network connection BUT does NOT stop the client network thread.
     * Client can reconnect or connect to a different server.
     */
    public void closeClient();

    /**
     * discover host (run as server) in LAN and WLAN.
     * broadcast a UDP message on the LAN to discover any servers (hosts) running.
     *
     * NOTE:
     *  + router problems: Some routers block broadcasts.
     *  + LAN: discovery only works on a LAN with the same subnet.
     *  + WLAN: discovery not possible if client-isolation-settings of the hotspot.
     */
    public List<InetAddress> discoverHosts();

}
