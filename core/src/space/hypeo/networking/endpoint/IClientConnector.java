package space.hypeo.networking.endpoint;

import java.net.InetAddress;
import java.util.List;

import space.hypeo.networking.packages.PlayerDisconnect;

/**
 * Interface provides service methods for player that is game-client
 */
public interface IClientConnector {

    /**
     * Joins the game another host has set up.
     * @param playerID
     * @return
     */
    boolean joinGame(String playerID);

    /**
     * discover host (run as server) in LAN and WLAN.
     * broadcast a UDP message on the LAN to discover any servers (hosts) running.
     *
     * NOTE:
     *  + router problems: Some routers block broadcasts.
     *  + LAN: discovery only works on a LAN with the same subnet.
     *  + WLAN: discovery not possible if client-isolation-settings of the hotspot.
     */
    List<InetAddress> discoverHosts();

    /**
     * Connects the client to the host.
     * @param hostAddr IP address of host
     */
    void connectToHost(InetAddress hostAddr);

    /**
     * Has client been established connection with host?
     * @return true when connected to host
     */
    boolean isConnected();

    /**
     * Disconnects the client from the host.
     */
    void disconnect();
}
