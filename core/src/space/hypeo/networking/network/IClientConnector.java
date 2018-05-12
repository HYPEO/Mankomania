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
     * Starts the client
     */
    public void startClient();

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
